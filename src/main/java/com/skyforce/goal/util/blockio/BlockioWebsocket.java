package com.skyforce.goal.util.blockio;

import com.google.gson.Gson;
import com.skyforce.goal.dto.money.blockio.SubscribeBalanceChangeDto;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.repository.WalletRepository;
import com.skyforce.goal.service.MoneySyncService;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;

public class BlockioWebsocket extends WebSocketClient {

    @Autowired
    private Gson gson;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private MoneySyncService moneySyncService;

    private SubscribeBalanceChangeDto subscribeBalanceChangeDto;

    public BlockioWebsocket() {
        super(URI.create("wss://n.block.io/"));
        this.subscribeBalanceChangeDto = new SubscribeBalanceChangeDto();
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
