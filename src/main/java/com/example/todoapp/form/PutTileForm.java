package com.example.todoapp.form;

import com.example.todoapp.domain.Card;
import com.example.todoapp.domain.Tile;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Data
public class PutTileForm {
    /**
     * タイルID
     */
    @Pattern(regexp = "^\\d+$")
    private String tile_id;
    /**
     * タイル名
     */
    @NotBlank
    private String name;
    /**
     * 所属するボードのID
     */
    private String board_id;
    /**
     * 所属するボードでのインデックス値
     */
    @NotBlank
    @Pattern(regexp = "^\\d+$")
    private String indicator;
    /**
     * 内包するカードの一覧
     */
    private List<Card> cards;

    public Tile toTile() {
        Tile tile = new Tile();
        tile.setTile_id(Integer.parseInt(tile_id));
        tile.setName(name);
        return tile;
    }
}
