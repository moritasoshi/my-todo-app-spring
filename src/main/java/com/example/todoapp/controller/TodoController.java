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
import java.util.Objects;

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
    @CrossOrigin("http://192.168.43.150:8081")
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
    @CrossOrigin("http://192.168.43.150:8081")
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
     * @param form ┗必須フィールド：name, card_id
     * @return 引数のcardを返す
     */
    @PutMapping("/card")
    @CrossOrigin("http://192.168.43.150:8081")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Card updateCard(@RequestBody @Validated PutCardForm form, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException("Bad Request");
        }

        Card card = form.toCard();
        // card_idが存在しない場合
        if (!todoService.containsCardId(card.getCard_id())) {
            throw new ConflictException(String.format("Conflict[card_id: %s doesn't exist]", card.getCard_id()));
        }
        // tile_idが存在しない場合
        if (!todoService.containsTileId(card.getTile_id())) {
            throw new ConflictException(String.format("Conflict[tile_id: %s doesn't exist]", card.getTile_id()));
        }
        // 任意のtileでindicatorが重複している場合
        if (todoService.hasDuplicateIndicator(card)) {
            throw new ConflictException(String.format("Conflict[Duplicate indicator: %s]", card.getIndicator()));
        }
        return todoService.update(card);
    }

    /**
     * @param tiles ┗必須フィールド：name, card_id
     * @return
     */
    @PutMapping("/tiles")
    @CrossOrigin("http://192.168.43.150:8081")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String updateTiles(@RequestBody List<Tile> tiles) {
        todoService.update(tiles);
        return null;
    }


    /////////////////////////////
    //// delete
    /////////////////////////////

    /**
     * @param id ┗必須フィールド：name, board_id
     * @return引数のboardを返す
     */
    @DeleteMapping("/board/{id}")
    @CrossOrigin("http://192.168.43.150:8081")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@PathVariable("id") String id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("Bad Request");
        }
        Integer parsedId;
        try {
            parsedId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Bad Request[id isn't integer]");
        }
        // board_idが存在しない場合
        if (!todoService.containsBoardId(parsedId)) {
            throw new ConflictException(String.format("Conflict[board_id: %s doesn't exist]", parsedId));
        }
        todoService.deleteBoard(parsedId);
    }

    /**
     * @param id ┗必須フィールド：name, tile_id
     * @return引数のtileを返す
     */
    @DeleteMapping("/tile/{id}")
    @CrossOrigin("http://192.168.43.150:8081")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTile(@PathVariable("id") String id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("Bad Request");
        }
        Integer parsedId;
        try {
            parsedId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Bad Request[id isn't integer]");
        }
        // board_idが存在しない場合
        if (!todoService.containsTileId(parsedId)) {
            throw new ConflictException(String.format("Conflict[tile_id: %s doesn't exist]", parsedId));
        }
        todoService.deleteTile(parsedId);
    }

    /**
     * @param id ┗必須フィールド：name, card_id
     * @return 引数のcardを返す
     */
    @DeleteMapping("/card/{id}")
    @CrossOrigin("http://192.168.43.150:8081")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable("id") String id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("Bad Request");
        }
        Integer parsedId;
        try {
            parsedId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Bad Request[id isn't integer]");
        }
        // board_idが存在しない場合
        if (!todoService.containsCardId(parsedId)) {
            throw new ConflictException(String.format("Conflict[card_id: %s doesn't exist]", parsedId));
        }
        todoService.deleteCard(parsedId);
    }
}
