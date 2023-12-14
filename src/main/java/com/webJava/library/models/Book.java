package com.webJava.library.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
@Entity
@Table(name = "Books")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_id")
    private List<User> users = new ArrayList<>();

    @Lob
    @Column(nullable = false)
    private String annotation;
    public Book(Image image, String title, String author, String annotation) {
        this.image = image;
        this.title = title;
        this.author = author;
        this.annotation = annotation;
    }
}
