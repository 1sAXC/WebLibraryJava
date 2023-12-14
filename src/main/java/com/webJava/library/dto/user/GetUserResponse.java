package com.webJava.library.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class GetUserResponse {

    private int id;

    private int roleId;

    private String roleName;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

}