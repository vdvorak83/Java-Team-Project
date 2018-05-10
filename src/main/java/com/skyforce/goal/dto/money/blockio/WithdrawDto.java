package com.skyforce.goal.dto.money.blockio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WithdrawDto implements Serializable {
    private String status;

    private WithdrawData data;

    @Data
    public static class WithdrawData implements Serializable{
        private String network;

        private String txid;

        @JsonProperty("amount_withdrawn")
        private String amountWithdrawn;

        @JsonProperty("amount_sent")
        private String amountSent;

        @JsonProperty("network_fee")
        private String networkFee;

        @JsonProperty("blockio_fee")
        private String blockioFee;
    }
}
