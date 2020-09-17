package com.example.todoapp.domain;

import lombok.Data;

import java.util.List;

@Data
public class Board {
    /**
     * ボードID
     */
    private Integer board_id;
    /**
     * ボード名
     */
    private String name;
    /**
     * ユーザーID
     */
    private Integer user_id;
    /**
     * 内包するタイルの一覧
     */
    private List<Tile> tiles;
}
