package com.example.todoapp.domain;

import lombok.Data;

import java.util.List;

@Data
public class Board {
    private String board_id;
    private String name;
    private String user_id;
    private List<Tile> tiles;
}
