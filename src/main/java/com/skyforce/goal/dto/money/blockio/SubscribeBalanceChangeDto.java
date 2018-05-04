package com.skyforce.goal.dto.money.blockio;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

public class SubscribeBalanceChangeDto implements Serializable {
    @Value("${blockio.api.key}")
    private String apiKey;

    private SubscribeBalanceChangeData data;
    private String stringRepresentation;

    public SubscribeBalanceChangeDto() {
        data = new SubscribeBalanceChangeData();
        data.setType("address");
        data.setApiKey(apiKey);
        Gson gson = new Gson();
        stringRepresentation = gson.toJson(data);
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    @Data
    private class SubscribeBalanceChangeData {
        private String type;

        @SerializedName("api_key")
        private String apiKey;
    }
}
