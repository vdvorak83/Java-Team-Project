package com.skyforce.goal.controller;

import com.skyforce.goal.dto.GoalDto;
import com.skyforce.goal.exception.InvalidFileException;
import com.skyforce.goal.model.Goal;
import com.skyforce.goal.model.Image;
import com.skyforce.goal.model.User;
import com.skyforce.goal.repository.GoalRepository;
import com.skyforce.goal.service.AuthenticationService;
import com.skyforce.goal.service.GoalService;
import com.skyforce.goal.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class GoalsController {
    @Value("${upload.file.directory}")
    private String uploadDirectory;

    private final AuthenticationService authenticationService;
    private final GoalService goalService;
    private final ImageService imageService;
    private final GoalRepository goalRepository;

    @Autowired
    public GoalsController(AuthenticationService authenticationService, GoalService goalService,
                           ImageService imageService, GoalRepository goalRepository) {
        this.authenticationService = authenticationService;
        this.goalService = goalService;
        this.imageService = imageService;
        this.goalRepository = goalRepository;
    }

    @GetMapping("/goals")
    public String getGoalsPage(Authentication authentication, Model model) {
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("goals", goalService.findAll());

        return "goals";
    }

    @GetMapping("/goal/new")
    public String getGoalCreatePage(Authentication authentication, Model model) {
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("goal", new GoalDto());

        return "new-goal";
    }

    @PostMapping("/goal/new")
    public String createGoal(@ModelAttribute("goal") GoalDto goalDto, @RequestParam("file") MultipartFile file,
                             Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            Image uploadedImage = imageService.uploadImage(file, uploadDirectory);

            imageService.save(uploadedImage);
            System.out.println("image uploaded successfully");
            goalService.save(authentication, goalDto, uploadedImage);
            System.out.println("goal saved successfully");
        } catch (InvalidFileException | IOException e) {
            redirectAttributes.addFlashAttribute("image.error",
                    "Invalid file extension. Please, select only *.jpg, *.jpeg or *.png files");

            return "redirect:/goal/new";
        }

        return "redirect:/user/profile";
    }

    @GetMapping("/goal/{id}")
    public String getGoalPage(Authentication authentication, Model model, @PathVariable("id") Long id) {
        Goal goal = goalService.findGoalById(id);

        model.addAttribute("goal", goal);
        model.addAttribute("user", authenticationService.getUserByAuthentication(authentication));
        model.addAttribute("all", goalService.findGoalsByUser(goal.getUser()));

        return "goal";
    }

    @GetMapping("/user/myGoals")
    public String getMyGoals(Authentication authentication, Model model) {
        User user = authenticationService.getUserByAuthentication(authentication);
        model.addAttribute("user", user);
        model.addAttribute("myGoals", goalService.findGoalsByUser(user));

        return "my-goals";
    }
}
