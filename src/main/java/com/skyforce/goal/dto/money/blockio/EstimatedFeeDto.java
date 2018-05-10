package com.skyforce.goal.dto.money.blockio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EstimatedFeeDto implements Serializable {
    private String status;

    private EstimatedFeeData data;

    @Data
    public static class EstimatedFeeData implements Serializable {
        private String network;
        @JsonProperty("estimated_network_fee")
        private String estimatedNetworkFee;
    }
}
