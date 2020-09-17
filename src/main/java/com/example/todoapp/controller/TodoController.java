package com.example.todoapp.controller;

import com.example.todoapp.domain.Board;
import com.example.todoapp.domain.Card;
import com.example.todoapp.domain.Tile;
import com.example.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    /**
     * @return 任意のユーザーのボード情報
     */
    @RequestMapping("/read/boards")
    public List<Board> boards() {
        return todoService.findAllBoard(1);
    }

    /**
     * @param board ┗必須フィールド：name
     * @return idが付与されたboardを返す
     */
    @PostMapping("/create/board")
    public Board createBoard(@RequestBody Board board) {
        return todoService.create(board);
    }

    /**
     * @param tile ┗必須フィールド：name, board_id
     * @return idが付与されたtileを返す
     */
    @PostMapping("/create/tile")
    public Tile createTile(@RequestBody Tile tile) {
        return todoService.create(tile);
    }

    /**
     * @param card ┗必須フィールド：name, tile_id
     * @return idが付与されたcardを返す
     */
    @PostMapping("/create/tile")
    public Card createCard(@RequestBody Card card) {
        return todoService.create(card);
    }

}
