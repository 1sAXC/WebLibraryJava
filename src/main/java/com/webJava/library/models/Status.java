package com.webJava.library.models;

import jakarta.persistence.*;
import lombok.Data;


@Table(name = "Status")
@Data
@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int count;
}
