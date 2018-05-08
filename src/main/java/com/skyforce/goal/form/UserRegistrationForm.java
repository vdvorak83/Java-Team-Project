package com.skyforce.goal.form;

import com.skyforce.goal.validation.PasswordMatches;
import com.skyforce.goal.validation.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@PasswordMatches
public class UserRegistrationForm {
    @NotNull
    @Size(min = 5, max = 50)
    private String login;

    @NotNull
    @Size(min = 6)
    private String password;

    @NotNull
    private String matchingPassword;

    private boolean checkEmail;

    @ValidEmail
    private String email;

}
