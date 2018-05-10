package com.skyforce.goal.dto.money.blockio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetBalanceDto implements Serializable {
    private String status;

    private GetBalanceData data;

    @Getter
    @Setter
    public static class GetBalanceData implements Serializable {
        private String network;

        @JsonProperty("available_balance")
        private String availableBalance;

        @JsonProperty("pending_received_balance")
        private String pendingReceivedBalance;

        private List<Balance> balances;

        @Getter
        @Setter
        public static class Balance implements Serializable {
            @JsonProperty("user_id")
            private Integer userId;

            private String label;

            private String address;

            @JsonProperty("available_balance")
            private String availableBalance;

            @JsonProperty("pending_received_balance")
            private String pendingReceivedBalance;
        }
    }
}
