package com.example.todoapp.domain;

import lombok.Data;

import java.util.List;

@Data
public class Board {
    /**
     * ボードID
     */
    private String board_id;
    /**
     * ボード名
     */
    private String name;
    /**
     * ユーザーID
     */
    private String user_id;
    /**
     * 内包するタイルの一覧
     */
    private List<Tile> tiles;
}
