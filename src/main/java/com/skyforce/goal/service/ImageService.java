package com.skyforce.goal.service;

import com.skyforce.goal.exception.InvalidFileException;
import com.skyforce.goal.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String getFileExtension(String fileName);

    boolean isValidExtension(String fileName) throws InvalidFileException;

    String handleFileName(String fileName) throws InvalidFileException;

    Image uploadImage(MultipartFile file, String uploadDirectory) throws InvalidFileException, IOException;

    void save(Image image);

    Image findImageByFileName(String fileName);
}
