package com.example.todoapp.form;

import com.example.todoapp.domain.Board;
import com.example.todoapp.domain.Tile;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class BoardForm {
    /**
     * ボードID
     */
    private String board_id;
    /**
     * ボード名
     */
    @NotBlank(message = "[name] field must not blank")
    private String name;
    /**
     * ユーザーUID
     */
    @NotBlank(message = "[user_uid] field must not blank")
    private String user_uid;
    /**
     * 内包するタイルの一覧
     */
    private List<Tile> tiles;

    public Board toBoard(){
        Board board = new Board();
        board.setBoard_id(Integer.parseInt(board_id));
        board.setName(name);
        board.setUser_uid(user_uid);
        board.setTiles(tiles);
        return board;
    }
}
