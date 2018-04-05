package com.skyforce.goal.repository;

import com.skyforce.goal.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findImageByFileName(String fileName);
}
