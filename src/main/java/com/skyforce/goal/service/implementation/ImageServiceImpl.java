package com.skyforce.goal.service.implementation;

import com.skyforce.goal.exception.InvalidFileException;
import com.skyforce.goal.model.Image;
import com.skyforce.goal.repository.ImageRepository;
import com.skyforce.goal.service.ImageService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.ContentType;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Value("${upload.file.extensions}")
    private String validExtensions;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex < 0) {
            return null;
        }

        return fileName.substring(dotIndex + 1);
    }

    @Override
    public boolean isValidExtension(String fileName) throws InvalidFileException {
        System.out.println("validating extension"); // FIXME: 05.04.2018
        String fileExtension = getFileExtension(fileName);

        if (fileExtension == null) {
            throw new InvalidFileException("Invalid file exception");
        }

        fileExtension = fileExtension.toLowerCase();

        for (String validExtension : validExtensions.split(", ")) {
            if (fileExtension.equals(validExtension)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String handleFileName(String fileName) {
        String extension = getFileExtension(fileName);
        String newImageName = UUID.randomUUID().toString();

        return newImageName + "." + extension;
    }

    @Override
    public Image uploadImage(MultipartFile file, String uploadDirectory) throws InvalidFileException, IOException {
        if (!isValidExtension(file.getOriginalFilename())) {
            throw new InvalidFileException("Invalid file exception");
        }

        String fileName = handleFileName(file.getOriginalFilename());
        Path path = Paths.get(uploadDirectory, fileName);

        Files.copy(file.getInputStream(), path);

        String extension = getFileExtension(fileName);
        String fileBaseName = fileName.substring(0, fileName.length() - extension.length() - 1);

        return new Image(uploadDirectory, fileName, extension, fileBaseName);
    }

    @Override
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Override
    public Image findImageByFileName(String fileName) {
        return imageRepository.findImageByFileName(fileName);
    }
}
