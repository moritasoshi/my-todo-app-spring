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

    /////////////////////////////
    //// read
    /////////////////////////////

    /**
     * @return 任意のユーザーのボード情報
     */
    @GetMapping("/read/boards")
    public List<Board> boards( String user_uid) {
        return todoService.findAllBoard(user_uid);
    }


    /////////////////////////////
    //// create
    /////////////////////////////

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
    @PostMapping("/create/card")
    public Card createCard(@RequestBody Card card) {
        return todoService.create(card);
    }


    /////////////////////////////
    //// update
    /////////////////////////////

    /**
     * @param board ┗必須フィールド：name, board_id
     * @return引数のboardを返す
     */
    @PostMapping("/update/board")
    public Board updateBoard(@RequestBody Board board) {
        return todoService.update(board);
    }

    /**
     * @param tile ┗必須フィールド：name, tile_id
     * @return引数のtileを返す
     */
    @PostMapping("/update/tile")
    public Tile updateTile(@RequestBody Tile tile) {
        return todoService.update(tile);
    }

    /**
     * @param card ┗必須フィールド：name, card_id
     * @return 引数のcardを返す
     */
    @PostMapping("/update/card")
    public Card updateCard(@RequestBody Card card) {
        return todoService.update(card);
    }

    /////////////////////////////
    //// delete
    /////////////////////////////

    /**
     * @param board ┗必須フィールド：name, board_id
     * @return引数のboardを返す
     */
    @PostMapping("/delete/board")
    public Board deleteBoard(@RequestBody Board board) {
        return todoService.delete(board);
    }

    /**
     * @param tile ┗必須フィールド：name, tile_id
     * @return引数のtileを返す
     */
    @PostMapping("/delete/tile")
    public Tile deleteTile(@RequestBody Tile tile) {
        return todoService.delete(tile);
    }

    /**
     * @param card ┗必須フィールド：name, card_id
     * @return 引数のcardを返す
     */
    @PostMapping("/delete/card")
    public Card deleteCard(@RequestBody Card card) {
        return todoService.delete(card);
    }
}
