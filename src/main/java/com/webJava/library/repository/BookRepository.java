package com.webJava.library.repository;

import com.webJava.library.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webJava.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findAllBy(Pageable pageable);
    Page<Book> findAllByUsers_Id(int users_id, Pageable pageable);
}
