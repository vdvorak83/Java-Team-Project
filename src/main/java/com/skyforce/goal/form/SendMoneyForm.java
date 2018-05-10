package com.skyforce.goal.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SendMoneyForm {
    private Integer id;

    private String address;

    private BigDecimal amount;
}
