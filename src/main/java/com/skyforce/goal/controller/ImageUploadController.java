package com.skyforce.goal.controller;

import com.skyforce.goal.exception.InvalidFileException;
import com.skyforce.goal.model.Image;
import com.skyforce.goal.model.User;
import com.skyforce.goal.repository.UserRepository;
import com.skyforce.goal.service.AuthenticationService;
import com.skyforce.goal.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Controller
public class ImageUploadController {
    @Value("${upload.file.directory}")
    private String uploadDirectory;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/storage/{file-name:.+}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getUploadedImage(@PathVariable("file-name") String fileName) throws IOException {

        Path path = imageService.findImageByFileName(fileName).getFilePath();

        return ResponseEntity
                .ok()
                .contentLength(Files.size(path))
                .contentType(
                        MediaType.parseMediaType(
                                URLConnection.guessContentTypeFromName(path.toString())
                        )
                )
                .body(new InputStreamResource(
                        Files.newInputStream(path, StandardOpenOption.READ))
                );
    }

    @PostMapping("/upload")
    public String handleImageUpload(@RequestParam("file") MultipartFile file, Authentication authentication,
                                    RedirectAttributes redirectAttributes) {
        try {
            Image uploadedImage = imageService.uploadImage(file, uploadDirectory);

            imageService.save(uploadedImage);

            User currentUser = authenticationService.getUserByAuthentication(authentication);

            currentUser.setImage(uploadedImage);
            userRepository.save(currentUser);
        } catch (InvalidFileException | IOException e) {
            redirectAttributes.addFlashAttribute("image.error",
                    "Invalid file extension. Please, select only *.jpg, *.jpeg or *.png files");

            return "redirect:/user/profile";
        }

        return "redirect:/user/profile";
    }
}
