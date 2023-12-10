package com.webJava.library.repository;

import com.webJava.library.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
}