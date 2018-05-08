package com.skyforce.goal.controller;

import com.skyforce.goal.form.SendMoneyForm;
import com.skyforce.goal.model.Transaction;
import com.skyforce.goal.model.User;
import com.skyforce.goal.model.Wallet;
import com.skyforce.goal.repository.TransactionRepository;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.repository.WalletRepository;
import com.skyforce.goal.security.role.UserRole;
import com.skyforce.goal.service.AuthenticationService;
import com.skyforce.goal.service.MoneyService;
import com.skyforce.goal.validator.SendMoneyFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MoneyController {
    private final AuthenticationService authenticationService;
    private final MoneyService moneyService;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    private final SendMoneyFormValidator sendMoneyFormValidator;

    @Autowired
    public MoneyController(AuthenticationService authenticationService, MoneyService moneyService,
                           UserRepository userRepository, WalletRepository walletRepository,
                           TransactionRepository transactionRepository, SendMoneyFormValidator sendMoneyFormValidator) {
        this.authenticationService = authenticationService;
        this.moneyService = moneyService;
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.sendMoneyFormValidator = sendMoneyFormValidator;
    }

    @GetMapping("/money")
    public String dashboard(Authentication authentication, Model model) {
        User user = authenticationService.getUserByAuthentication(authentication);
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        List<Transaction> transactions = transactionRepository.findAll().stream()
                .filter(t -> t.getUser().equals(user)).collect(Collectors.toList());
        Collections.reverse(transactions);
        model.addAttribute("transactions", transactions);
        return "money";
    }

    @GetMapping("/admin/money")
    public String adminMoney(Authentication authentication, Model model) {
        List<Wallet> wallets = walletRepository.findAll();
        model.addAttribute("users", userRepository.findAll().stream()
                .filter(u -> u.getRole().equals(UserRole.USER))
                .collect(Collectors.toList()));
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        return "admin-money";
    }

    @PostMapping("/money/create")
    public String createWallet(Authentication authentication, Model model, @RequestParam(value = "id", required = false) Integer id) {
        User loggedUser = authenticationService.getUserByAuthentication(authentication);
        User user;
        if (id == null) {
            user = loggedUser;
        } else {
            user = userRepository.findUserById(id);
        }
        if (user.getRole().equals(UserRole.USER) && (loggedUser.getId().equals(user.getId())) || !loggedUser.getRole().equals(UserRole.USER)) {
            moneyService.createWallet(user);
        } else {
            // TODO Better error handling.
            return "404";
        }
        model.addAttribute("users", userRepository.findAll().stream()
                .filter(u -> u.getRole().equals(UserRole.USER))
                .collect(Collectors.toList()));
        model.addAttribute("user", loggedUser);
        return "admin-money";
    }

    @PostMapping("/money/send")
    public String sendMoney(Authentication authentication, Model model, @ModelAttribute("sendMoneyForm") @Valid SendMoneyForm form) {
        User loggedUser = authenticationService.getUserByAuthentication(authentication);
        User user = userRepository.findUserById(form.getId());
        if (user == null) {
            user = loggedUser;
        }
        moneyService.sendMoney(user, form.getAddress(), form.getAmount());

        model.addAttribute("users", userRepository.findAll().stream()
                .filter(u -> u.getRole().equals(UserRole.USER))
                .collect(Collectors.toList()));
        model.addAttribute("user", loggedUser);
        return "redirect:/money";
    }

    @InitBinder("sendMoneyForm")
    public void initUserFormValidator(WebDataBinder binder) {
        binder.addValidators(sendMoneyFormValidator);
    }
}
