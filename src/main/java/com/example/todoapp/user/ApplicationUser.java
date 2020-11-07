package com.example.todoapp.user;

import lombok.Data;

@Data
public class ApplicationUser {
    private long id;
    private String uid;
    private String password;
}