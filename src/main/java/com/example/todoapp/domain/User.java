package com.example.todoapp.domain;

import lombok.Data;

@Data
public class User {
    /**
     * ユーザーID
     */
    private String id;
    /**
     * Googleアカウントで保持されるuid
     */
    private String uid;
}
