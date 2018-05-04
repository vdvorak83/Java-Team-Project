package com.skyforce.goal.dto.money.blockio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateWalletDto implements Serializable {
    private String status;

    private CreateWalletData data;

    @Data
    public static class CreateWalletData implements Serializable {
        private String network;

        @JsonProperty("user_id")
        private String userId;

        private String address;

        private String label;
    }
}
