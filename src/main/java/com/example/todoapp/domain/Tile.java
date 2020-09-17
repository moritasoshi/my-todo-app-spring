package com.example.todoapp.domain;

import lombok.Data;

import java.util.List;

@Data
public class Tile {
    private String tile_id;
    private String name;
    private List<Card> cards;
}
