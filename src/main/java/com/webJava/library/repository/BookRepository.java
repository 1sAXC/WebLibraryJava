package com.webJava.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webJava.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Page<Book> findAllByUser_Id(int userId, Pageable pageable);
    Page<Book> getAll(Pageable pageable);
}
