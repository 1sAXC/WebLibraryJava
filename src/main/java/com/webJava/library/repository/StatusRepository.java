package com.webJava.library.repository;

import com.webJava.library.models.Book;
import com.webJava.library.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
