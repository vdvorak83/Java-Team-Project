package com.skyforce.goal.controller;

import com.skyforce.goal.hibernate.search.GoalSearch;
import com.skyforce.goal.model.Goal;
import com.skyforce.goal.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {
    private final AuthenticationService authenticationService;
    private final GoalSearch goalSearch;

    @Autowired
    public SearchController(AuthenticationService authenticationService, GoalSearch goalSearch) {
        this.authenticationService = authenticationService;
        this.goalSearch = goalSearch;
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "query", required = false) String query,
                         Authentication authentication) {
        List goalSearchResult = null;

        try {
            goalSearchResult = goalSearch.search(query);
        } catch (Exception ignored) {

        }

        model.addAttribute(authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("goalSearchResults", goalSearchResult);

        return "search";
    }
}
