package com.skyforce.goal.util.blockio;

import com.google.gson.Gson;
import com.skyforce.goal.dto.money.blockio.SubscribeBalanceChangeDto;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.repository.WalletRepository;
import com.skyforce.goal.service.MoneySyncService;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@Scope("singleton")
public class BlockioWebsocket extends WebSocketClient {

    private final Gson gson;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final MoneySyncService moneySyncService;
    private SubscribeBalanceChangeDto subscribeBalanceChangeDto;

    @Autowired
    public BlockioWebsocket(Gson gson, UserRepository userRepository, WalletRepository walletRepository, MoneySyncService moneySyncService) {
        super(URI.create("wss://n.block.io/"));
        this.subscribeBalanceChangeDto = new SubscribeBalanceChangeDto();
        this.gson = gson;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.moneySyncService = moneySyncService;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String s) {
        if (s.contains("\"status\": \"success\"")) {
            // If successfully connected to block.io Web Socket server subscribe to balance change of all linked to
            // api key wallets.
            this.send(subscribeBalanceChangeDto.toString());
        } else if (s.contains("\"type\": \"address\"")) {
            // If received notification about balance change.

            moneySyncService.updateTransactions();
//            BalanceChangeDto dto = gson.fromJson(s, BalanceChangeDto.class);
//            BigDecimal change = new BigDecimal(dto.getData().getBalanceChange());
//            Wallet wallet = walletRepository.findWalletByAddress(dto.getData().getAddress());
//            if (wallet != null) {
//                User user = userRepository.findUserByWallet(wallet);
//                if (user != null) {
//                    if (!dto.getData().getBalanceChange().contains("-")) {
//                        user.setMoney(user.getMoney().add(change));
//                        userRepository.save(user);
//                    }
//                }
//            }
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
