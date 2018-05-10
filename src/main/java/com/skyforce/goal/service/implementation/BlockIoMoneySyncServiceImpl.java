package com.skyforce.goal.service.implementation;

import com.skyforce.goal.dto.money.blockio.GetBalanceDto;
import com.skyforce.goal.dto.money.blockio.ReceivedTransactionBlockioDto;
import com.skyforce.goal.dto.money.blockio.SentTransactionBlockioDto;
import com.skyforce.goal.model.Transaction;
import com.skyforce.goal.model.User;
import com.skyforce.goal.model.Wallet;
import com.skyforce.goal.model.enums.TransactionState;
import com.skyforce.goal.repository.TransactionRepository;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.repository.WalletRepository;
import com.skyforce.goal.service.MoneySyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlockIoMoneySyncServiceImpl implements MoneySyncService {
    private final static String URL = "https://block.io";
    private final static String GET_BALANCE = "/api/v2/get_address_balance/?api_key=%s&addresses=%s";
    private final static String GET_TRANSACTIONS_RECEIVED = "/api/v2/get_transactions/?api_key=%s&type=received";
    private final static String GET_TRANSACTIONS_RECEIVED_BEFORE = "/api/v2/get_transactions/?api_key=%s&type=received&before_tx=%s";
    private final static String GET_TRANSACTIONS_SENT = "/api/v2/get_transactions/?api_key=%s&type=sent";
    private final static String GET_TRANSACTIONS_SENT_BEFORE = "/api/v2/get_transactions/?api_key=%s&type=sent&before_tx=%s";
    private final RestTemplate restTemplate;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    @Value("${blockio.api.key}")
    private String API_KEY;

    @Autowired
    public BlockIoMoneySyncServiceImpl(RestTemplate restTemplate,
                                       TransactionRepository transactionRepository, UserRepository userRepository,
                                       WalletRepository walletRepository) {
        this.restTemplate = restTemplate;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    //    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void syncBalances() {
        List<User> users = userRepository.findAll();
        LinkedList<Wallet> wallets = new LinkedList<>();
        StringBuilder walletsSb = new StringBuilder();
        for (User user : users) {
            Wallet wallet = user.getWallet();
            if (wallet == null || wallet.getAddress() == null)
                continue;
            wallets.addLast(wallet);
            walletsSb.append(wallet.getAddress());
            walletsSb.append(',');
        }
        walletsSb.deleteCharAt(walletsSb.length() - 1);
        String requestUrl = String.format(URL + GET_BALANCE, API_KEY, walletsSb.toString());
        GetBalanceDto dto;
        try {
            dto = restTemplate.getForObject(requestUrl, GetBalanceDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (dto.getData() == null)
            return;
        for (Wallet wallet : wallets) {
            Optional<GetBalanceDto.GetBalanceData.Balance> balance = dto.getData().getBalances().stream()
                    .filter(b -> b.getAddress().equals(wallet.getAddress())).findFirst();
            if (balance.isPresent() && wallet.getBalance().compareTo(new BigDecimal(balance.get().getAvailableBalance())) != 0) {
                System.err.println("Our and Block.io balance of this wallet does do not match" + wallet.getAddress());
                wallet.setBalance(new BigDecimal(balance.get().getAvailableBalance()));
                walletRepository.save(wallet);
            }
        }
    }

    // Starts at boot
    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void updateTransactions() {
        String requestUrl = String.format(URL + GET_TRANSACTIONS_RECEIVED, API_KEY);
        ReceivedTransactionBlockioDto dtoReceived;
        try {
            dtoReceived = restTemplate.getForObject(requestUrl, ReceivedTransactionBlockioDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        requestUrl = String.format(URL + GET_TRANSACTIONS_SENT, API_KEY);
        SentTransactionBlockioDto dtoSent;
        try {
            dtoSent = restTemplate.getForObject(requestUrl, SentTransactionBlockioDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Date lastSentDate;
        Date lastReceivedDate;
        Transaction lastSentTransaction = transactionRepository.findFirst1ByStateOrderByDateDesc(TransactionState.SENT);
        Transaction lastReceivedTransaction = transactionRepository.findFirst1ByStateOrderByDateDesc(TransactionState.RECEIVED);
        if (lastReceivedTransaction == null) {
            if (lastSentTransaction == null) {
                // If there is no transaction history, all wallets should start with zero balance.
                for (Wallet wallet : walletRepository.findAll().stream().filter(Wallet::isOur)
                        .collect(Collectors.toCollection(ArrayList::new))) {
                    wallet.setBalance(new BigDecimal(0));
                }
            }
        }
        List<Transaction> transactionsWithLastDate = null;
        if (lastSentTransaction != null) {
            lastSentDate = lastSentTransaction.getDate();
            transactionsWithLastDate = transactionRepository.findAllByDate(lastSentDate);
        } else {
            lastSentDate = new Date(0);
        }
        if (lastReceivedTransaction != null) {
            lastReceivedDate = lastReceivedTransaction.getDate();
            transactionsWithLastDate = transactionRepository.findAllByDate(lastReceivedDate);
        } else {
            lastReceivedDate = new Date(0);
        }
        List<Transaction> transactions = new ArrayList<>();

        List<ReceivedTransactionBlockioDto.TransactionsBlockioData.TransactionBlockio> transactionsReceived = dtoReceived.getData().getTransactions();
        boolean found = false;
        do {
            for (ReceivedTransactionBlockioDto.TransactionsBlockioData.TransactionBlockio transaction : transactionsReceived) {
                Date transactionDate = new Date(transaction.getTime() * 1000);
                if (lastReceivedDate.compareTo(transactionDate) > 0) {
                    found = true;
                    break;
                }

                String fromAddress = transaction.getSenders().get(0);
                Wallet fromWallet = saveWallet(fromAddress);
                Wallet toWallet = saveWallet(transaction.getAmounts().get(0).getRecipient());
                User user = userRepository.findUserByWallet(toWallet);

                BigDecimal amount = new BigDecimal(0);
                for (ReceivedTransactionBlockioDto.TransactionsBlockioData.TransactionBlockio.AmountBlockio amountReceived
                        : transaction.getAmounts()) {
                    amount = amount.add(new BigDecimal(amountReceived.getAmount()));
                }

                Transaction newTransaction = Transaction.builder()
                        .walletTo(toWallet)
                        .walletFrom(fromWallet)
                        .amount(amount)
                        .date(transactionDate)
                        .user(userRepository.findUserByWallet(toWallet))
                        .txid(transaction.getTxid())
                        .state(TransactionState.RECEIVED)
                        .build();
                transactions.add(newTransaction);

                user.setMoney(user.getMoney().add(amount));
                toWallet.setBalance(toWallet.getBalance().add(amount));
                userRepository.save(user);
                walletRepository.save(toWallet);
            }
            if (!found && transactionsReceived.size() == 25) {
                requestUrl = String.format(URL + GET_TRANSACTIONS_RECEIVED_BEFORE, API_KEY,
                        transactions.get(transactions.size() - 1).getTxid());
                try {
                    dtoReceived = restTemplate.getForObject(requestUrl, ReceivedTransactionBlockioDto.class);
                    transactionsReceived = dtoReceived.getData().getTransactions();
                } catch (Exception e) {
                    return;
                }
            } else {
                found = true;
            }
        } while (!found);

        List<SentTransactionBlockioDto.TransactionsBlockioData.TransactionBlockio> transactionsSent = dtoSent.getData().getTransactions();
        found = false;
        do {
            for (SentTransactionBlockioDto.TransactionsBlockioData.TransactionBlockio transaction : transactionsSent) {
                Date transactionDate = new Date(transaction.getTime() * 1000);
                if (lastSentDate.compareTo(transactionDate) > 0) {
                    found = true;
                    break;
                }

                String fromAddress = transaction.getSenders().get(0);
                Wallet fromWallet = saveWallet(fromAddress);
                Wallet toWallet = saveWallet(transaction.getAmounts().get(0).getRecipient());
                User user = userRepository.findUserByWallet(fromWallet);

                BigDecimal amount = new BigDecimal(0);
                for (SentTransactionBlockioDto.TransactionsBlockioData.TransactionBlockio.AmountBlockio amountReceived
                        : transaction.getAmounts()) {
                    amount = amount.add(new BigDecimal(amountReceived.getAmount()));
                }

                Transaction newTransaction = Transaction.builder()
                        .walletFrom(fromWallet)
                        .walletTo(toWallet)
                        .amount(amount)
                        .fee(new BigDecimal(transaction.getAmountTotal()).subtract(amount))
                        .date(transactionDate)
                        .user(user)
                        .txid(transaction.getTxid())
                        .state(TransactionState.SENT)
                        .build();
                transactions.add(newTransaction);

                user.setMoney(user.getMoney().subtract(new BigDecimal(transaction.getAmountTotal())));
                fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
                userRepository.save(user);
                walletRepository.save(fromWallet);
            }
            if (!found && transactionsSent.size() == 25) {
                requestUrl = String.format(URL + GET_TRANSACTIONS_SENT_BEFORE, API_KEY,
                        transactions.get(transactions.size() - 1).getTxid());
                try {
                    dtoSent = restTemplate.getForObject(requestUrl, SentTransactionBlockioDto.class);
                    transactionsSent = dtoSent.getData().getTransactions();
                } catch (Exception e) {
                    return;
                }
            } else {
                found = true;
            }
        } while (!found);

        transactions.sort(Comparator.comparing(Transaction::getDate));
        if (transactionsWithLastDate != null) {
            for (Transaction transaction : transactions) {
                if (transactionsWithLastDate.stream().noneMatch(t -> t.getTxid().equals(transaction.getTxid())))
                    transactionRepository.save(transaction);
            }
        } else {
            transactionRepository.saveAll(transactions);
        }
    }

    @Override
    public void resyncTransactions() {
        transactionRepository.deleteAll();
        updateTransactions();
    }

    /**
     * Save wallet address to not repeat it in database, but only mention it's id.
     *
     * @param address Bitcoin address
     */
    @Override
    public Wallet saveWallet(String address) {

        Wallet wallet = walletRepository.findWalletByAddress(address);
        if (wallet == null) {
            wallet = new Wallet();
            wallet.setAddress(address);
            wallet.setBalance(new BigDecimal(0));
        }
        walletRepository.save(wallet);
        return wallet;
    }
}
