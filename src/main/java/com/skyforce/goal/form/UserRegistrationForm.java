package com.skyforce.goal.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserRegistrationForm {
    private String login;
    private String password;
    private String email;
}
