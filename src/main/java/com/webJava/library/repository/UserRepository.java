package com.webJava.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webJava.library.models.User;
import com.webJava.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Page<User> findAllByRole_Name(String name, Pageable pageable);
    Optional<User> findAllByUsername(String username);

    List<User> findUsersByBooksId(int books_id);
}
