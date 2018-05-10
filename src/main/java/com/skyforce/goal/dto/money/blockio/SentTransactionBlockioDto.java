package com.skyforce.goal.dto.money.blockio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.List;

@Data
public class SentTransactionBlockioDto implements Serializable {
    private String status;

    private TransactionsBlockioData data;

    @Data
    public static class TransactionsBlockioData {
        private String network;

        @JsonProperty("txs")
        private List<TransactionBlockio> transactions;

        @Data
        public static class TransactionBlockio implements Serializable {
            private String txid;
            @JsonProperty("from_green_address")
            private boolean fromGreenAddress;
            private Long time;
            private Integer confirmations;
            @JsonProperty("total_amount_sent")
            private String amountTotal;
            @JsonProperty("amounts_sent")
            private List<AmountBlockio> amounts;
            private List<String> senders;
            private Float confidence;
            @JsonProperty("propagated_by_nodes")
            private Object propagatedByNodes;

            @Data
            public static class AmountBlockio implements Serializable {
                private String recipient;

                private String amount;
            }
        }
    }
}
