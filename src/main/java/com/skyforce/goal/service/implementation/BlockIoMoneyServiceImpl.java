package com.skyforce.goal.service.implementation;

import com.skyforce.goal.dto.money.blockio.CreateWalletDto;
import com.skyforce.goal.dto.money.blockio.EstimatedFeeDto;
import com.skyforce.goal.dto.money.blockio.SubscribeBalanceChangeDto;
import com.skyforce.goal.dto.money.blockio.WithdrawDto;
import com.skyforce.goal.model.Transaction;
import com.skyforce.goal.model.User;
import com.skyforce.goal.model.Wallet;
import com.skyforce.goal.model.enums.TransactionState;
import com.skyforce.goal.repository.TransactionRepository;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.repository.WalletRepository;
import com.skyforce.goal.service.MoneyService;
import com.skyforce.goal.service.MoneySyncService;
import com.skyforce.goal.util.blockio.BlockioWebsocket;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class BlockIoMoneyServiceImpl implements MoneyService {
    private final static String URL = "https://block.io";
    private final static String CREATE_WALLET = "/api/v2/get_new_address/?api_key=%s";
    private final static String ESTIMATE_FEE = "/api/v2/get_network_fee_estimate/?api_key=%s&to_addresses=%s&amounts=%s";
    private final static String WITHDRAW = "/api/v2/withdraw_from_addresses/?api_key=%s&pin=%s&from_addresses=%s" +
            "&to_addresses=%s&amounts=%s";

    private final static Pattern bitcoinRegex = Pattern.compile("^[123][a-km-zA-HJ-NP-Z1-9]{25,34}$");
    private final static BigDecimal minAmount = new BigDecimal(0.0002);
    private final RestTemplate restTemplate;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final BlockioWebsocket blockioWebsocket;
    private final SubscribeBalanceChangeDto subscribeBalanceChangeDto;
    private final MoneySyncService moneySyncService;
    @Value("${blockio.api.key}")
    private String API_KEY;
    @Value("${blockio.pin}")
    private String PIN;

    @Autowired
    public BlockIoMoneyServiceImpl(RestTemplate restTemplate,
                                   TransactionRepository transactionRepository, UserRepository userRepository,
                                   WalletRepository walletRepository, BlockioWebsocket blockioWebsocket,
                                   MoneySyncService moneySyncService) {
        this.restTemplate = restTemplate;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.blockioWebsocket = blockioWebsocket;
        this.moneySyncService = moneySyncService;
        this.subscribeBalanceChangeDto = new SubscribeBalanceChangeDto();
    }

    @Override
    public boolean createWallet(User user) {
        if (user == null) return false;
        String requestUrl = String.format(URL + CREATE_WALLET, API_KEY);
        CreateWalletDto dto;
        try {
            dto = restTemplate.getForObject(requestUrl, CreateWalletDto.class);
        } catch (Exception e) {
            return false;
        }
        if (dto != null && dto.getStatus().equals("success")) {
            blockioWebsocket.send(subscribeBalanceChangeDto.toString());
            Wallet wallet;
            if (user.getWallet() == null) {
                wallet = new Wallet();
            } else {
                wallet = user.getWallet();
            }
            if (!bitcoinRegex.matcher(dto.getData().getAddress()).matches()) return false;
            wallet.setAddress(dto.getData().getAddress());
            user.setWallet(wallet);
            walletRepository.save(wallet);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean sendMoney(User user, String address, BigDecimal amount) {
        // Validate input.
        if (user == null || user.getWallet() == null || user.getWallet().getAddress() == null
                || !bitcoinRegex.matcher(address).matches() || amount.compareTo(minAmount) < 0)
            return false;

        String requestUrl = String.format(URL + WITHDRAW, API_KEY, PIN, user.getWallet().getAddress(), address,
                amount.toString());

        Wallet walletTo = moneySyncService.saveWallet(address);
        Wallet walletFrom = user.getWallet();
        Hibernate.initialize(walletFrom.getId());
        Hibernate.initialize(walletFrom.getAddress());
        walletFrom.setId(walletFrom.getId());

        BigDecimal fee = estimateFee(address, amount);
        if (fee == null)
            return false;

        amount = amount.subtract(fee);

        // User should have more money in wallet than sending amount + fee.
        if (user.getMoney().compareTo(amount) < 0) {
            return false;
        }

        // Add transaction to history.
        Transaction transaction = Transaction.builder()
                .user(user)
                .walletTo(walletTo)
                .walletFrom(walletFrom)
                .amount(amount)
                .fee(fee)
                .date(new Date())
                .build();

        WithdrawDto dto;
        try {
            dto = restTemplate.getForObject(requestUrl, WithdrawDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            transaction.setState(TransactionState.SEND_FAILED);
            transactionRepository.save(transaction);
            return false;
        }

        if (dto.getStatus().equals("success")) {
            //            transaction.setState(TransactionState.SENT);
            //            user.setMoney(user.getMoney().subtract(amount.add(fee)));
            moneySyncService.updateTransactions();
            return true;
        } else {
            transaction.setState(TransactionState.SEND_FAILED);
            transactionRepository.save(transaction);
            return false;
        }
    }

    @Override
    public BigDecimal estimateFee(String address, BigDecimal amount) {
        // Validate input.
        if (!bitcoinRegex.matcher(address).matches() || amount.compareTo(minAmount) < 0)
            return null;

        String requestUrl = String.format(URL + ESTIMATE_FEE, API_KEY, address, amount.toString());

        EstimatedFeeDto dto;
        try {
            dto = restTemplate.getForObject(requestUrl, EstimatedFeeDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if (dto.getStatus().equals("success")) {
            return new BigDecimal(dto.getData().getEstimatedNetworkFee());
        } else {
            return null;
        }
    }
}
