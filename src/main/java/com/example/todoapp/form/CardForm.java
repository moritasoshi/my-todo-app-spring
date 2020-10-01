package com.example.todoapp.form;

import com.example.todoapp.domain.Card;
import com.example.todoapp.domain.Tile;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Data
public class CardForm {
    // Field
    /**
     * カードID
     */
    private String card_id;

    /**
     * カード名
     */
    @NotBlank
    private String name;

    /**
     * 所属するタイルのID
     */
    @Pattern(regexp = "^\\d+$")
    private String tile_id;

    /**
     * 所属するタイルでのインデックス値
     */
    private String indicator;

    // Method
    public Card toCard() {
        Card card = new Card();
        if (Objects.nonNull(card_id)) {
            card.setCard_id(Integer.parseInt(card_id));
        }
        card.setName(name);
        card.setTile_id(Integer.parseInt(tile_id));
        card.setIndicator(Integer.parseInt(indicator));
        return card;
    }
}
