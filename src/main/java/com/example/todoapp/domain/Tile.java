package com.example.todoapp.domain;

import lombok.Data;

import java.util.List;

@Data
public class Tile {
    /**
     * タイルID
     */
    private String tile_id;
    /**
     * タイル名
     */
    private String name;
    /**
     * 所属するボードのID
     */
    private String board_id;
    /**
     * 内包するカードの一覧
     */
    private List<Card> cards;
}
