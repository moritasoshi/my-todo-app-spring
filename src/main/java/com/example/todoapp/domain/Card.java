package com.example.todoapp.domain;

import lombok.Data;

@Data
public class Card {
    /**
     * カードID
     */
    private Integer card_id;
    /**
     * カード名
     */
    private String name;
    /**
     * 所属するタイルのID
     */
    private Integer tile_id;
}
