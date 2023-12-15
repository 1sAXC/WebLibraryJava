package com.webJava.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.webJava.library.models.Post;
public interface PostRepository extends JpaRepository<Post, Integer>{

}
