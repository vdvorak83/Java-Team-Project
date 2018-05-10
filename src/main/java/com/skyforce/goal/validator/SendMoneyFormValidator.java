package com.skyforce.goal.validator;

import com.skyforce.goal.dto.GoalDto;
import com.skyforce.goal.form.SendMoneyForm;
import com.skyforce.goal.model.Goal;
import com.skyforce.goal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class SendMoneyFormValidator implements Validator {
    private final static Pattern bitcoinRegex = Pattern.compile("^[123][a-km-zA-HJ-NP-Z1-9]{25,34}$");
    private final static BigDecimal minAmount = new BigDecimal(0.0002);

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.getName().equals(SendMoneyForm.class.getName());
    }

    @Override
    public void validate(Object o, Errors errors) {
        SendMoneyForm sendMoneyForm = (SendMoneyForm) o;

        if(!bitcoinRegex.matcher(sendMoneyForm.getAddress()).matches()){
            errors.reject("invalid.address", "Invalid BitCoin address.");
        }

        if(sendMoneyForm.getAmount().compareTo(minAmount)<0){
            errors.reject("wrong.amount", "Withdraw amount should be greater than 0.0002 BTC.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"address","empty.goalName",
                "Goal name can't be empty!");
    }
}
