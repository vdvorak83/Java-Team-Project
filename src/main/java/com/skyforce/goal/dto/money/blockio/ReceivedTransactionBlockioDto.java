package com.skyforce.goal.dto.money.blockio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
public class ReceivedTransactionBlockioDto implements Serializable {
    private String status;

    private TransactionsBlockioData data;

    @Data
    public static class TransactionsBlockioData {
        private String network;

        @JsonProperty("txs")
        private List<TransactionBlockio> transactions;

        @Data
        public static class TransactionBlockio implements Serializable {
            private Float confidence;
            @JsonProperty("propagated_by_nodes")
            private Object propagatedByNodes;
            private List<String> senders;
            private String txid;
            @JsonProperty("from_green_address")
            private boolean fromGreenAddress;
            private Long time;
            private Integer confirmations;
            @JsonProperty("amounts_received")
            private List<AmountBlockio> amounts;

            @Data
            public static class AmountBlockio implements Serializable {
                private String recipient;

                private String amount;
            }
        }
    }
}
