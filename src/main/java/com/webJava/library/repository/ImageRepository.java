package com.webJava.library.repository;

import com.webJava.library.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
