package com.example.todoapp.controller;

import com.example.todoapp.domain.Board;
import com.example.todoapp.domain.Card;
import com.example.todoapp.domain.Tile;
import com.example.todoapp.exception.BadRequestException;
import com.example.todoapp.exception.ConflictException;
import com.example.todoapp.form.*;
import com.example.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
     * status success => 200
     *
     * @return 任意のユーザーのボード情報
     */
    @GetMapping("/boards/{user_uid}")
    @ResponseStatus(HttpStatus.OK)
    public List<Board> getBoards(@PathVariable("user_uid") String user_uid) {
        List<Board> boards = todoService.findAllBoard(user_uid);
        return boards;
    }


    /////////////////////////////
    //// create
    /////////////////////////////

    /**
     * @param form ┗必須フィールド：name
     * @return idが付与されたboardを返す
     */
    @PostMapping("/board")
    @ResponseStatus(HttpStatus.CREATED)
    public Board createBoard(@RequestBody @Validated BoardForm form, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Bad Request");
        }
        return todoService.create(form.toBoard());
    }

    /**
     * @param form ┗必須フィールド：name, board_id
     * @return idが付与されたtileを返す
     */
    @PostMapping("/tile")
    @ResponseStatus(HttpStatus.CREATED)
    public Tile createTile(@RequestBody @Validated TileForm form, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Bad Request");
        }
        return todoService.create(form.toTile());
    }

    /**
     * @param form ┗必須フィールド：name, tile_id
     * @return idが付与されたcardを返す
     */
    @PostMapping("/card")
    @ResponseStatus(HttpStatus.CREATED)
    public Card createCard(@RequestBody @Validated CardForm form, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Bad Request");
        }
        return todoService.create(form.toCard());
    }


    /////////////////////////////
    //// update
    /////////////////////////////

    /**
     * @param form ┗必須フィールド：name, board_id
     * @return引数のboardを返す
     */
    @PutMapping("/board")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Board updateBoard(@RequestBody @Validated PutBoardForm form, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Bad Request");
        }

        Board board = form.toBoard();
        if (!todoService.containsBoardId(board.getBoard_id())) {
            throw new ConflictException(String.format("Conflict[board_id: %s doesn't exist]", board.getBoard_id()));
        }
        return todoService.update(board);
    }

    /**
     * @param form ┗必須フィールド：name, tile_id
     * @return引数のtileを返す
     */
    @PutMapping("/tile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Tile updateTile(@RequestBody @Validated PutTileForm form, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Bad Request");
        }

        Tile tile = form.toTile();
        if (!todoService.containsTileId(tile.getTile_id())) {
            throw new ConflictException(String.format("Conflict[tile_id: %s doesn't exist]", tile.getTile_id()));
        }
        return todoService.update(tile);
    }

    /**
     * @param card ┗必須フィールド：name, card_id
     * @return 引数のcardを返す
     */
    @PutMapping("/card")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Card updateCard(@RequestBody Card card) {
        return todoService.update(card);
    }

    /**
     * @param tiles ┗必須フィールド：name, card_id
     * @return
     */
    @PutMapping("/tiles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String updateTiles(@RequestBody List<Tile> tiles) {
        todoService.update(tiles);
        return "OK";
    }


    /////////////////////////////
    //// delete
    /////////////////////////////

    /**
     * @param id ┗必須フィールド：name, board_id
     * @return引数のboardを返す
     */
    @DeleteMapping("/board/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable("id") Integer id) {
        todoService.deleteBoard(id);
    }

    /**
     * @param id ┗必須フィールド：name, tile_id
     * @return引数のtileを返す
     */
    @DeleteMapping("/tile/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTile(@PathVariable("id") Integer id) {
        todoService.deleteTile(id);
    }

    /**
     * @param id ┗必須フィールド：name, card_id
     * @return 引数のcardを返す
     */
    @DeleteMapping("/card/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable("id") Integer id) {
        todoService.deleteCard(id);
    }
}
