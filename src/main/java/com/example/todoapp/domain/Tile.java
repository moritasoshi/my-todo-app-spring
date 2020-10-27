package com.example.todoapp.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Tile {
    /**
     * タイルID
     */
    private Integer tile_id;
    /**
     * タイル名
     */
    private String name;
    /**
     * 所属するボードのID
     */
    private Integer board_id;
    /**
     * 所属するボードでのインデックス値
     */
    private Integer indicator;
    /**
     * 内包するカードの一覧
     */
    private List<Card> cards;
}
