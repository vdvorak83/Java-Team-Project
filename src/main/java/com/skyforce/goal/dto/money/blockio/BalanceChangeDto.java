package com.skyforce.goal.dto.money.blockio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BalanceChangeDto implements Serializable {
    private String address;

    private BalanceChangeData data;

    @Data
    public static class BalanceChangeData implements Serializable {
        private String network;

        private String address;

        @JsonProperty("balance_change")
        private String balanceChange;

        @JsonProperty("amount_sent")
        private String amountSent;

        @JsonProperty("amount_received")
        private String amountReceived;

        private String txid;

        private byte confirmations;

        @JsonProperty("is_green")
        private boolean isGreen;
    }
}
