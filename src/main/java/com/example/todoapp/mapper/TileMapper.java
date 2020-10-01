package com.example.todoapp.mapper;

import com.example.todoapp.domain.Board;
import com.example.todoapp.domain.Card;
import com.example.todoapp.domain.Tile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TileMapper {
    /**
     * board_idをもとにtileを新規作成する
     *
     * @param tile
     */
    void create(Tile tile);

    /**
     * 新規作成されたtileのidを取得する
     *
     * @return 自動採番されたid
     */
    Integer getLastInsertId();

    /**
     * Tile名の変更
     * @param tile
     */
    void update(Tile tile);

    /**
     * Tileの削除
     * @param tileId
     */
    void delete(Integer tileId);

    /**
     * 任意のBoardIDに一致する全てのタイルを検索する
     * @param boardId
     */
    List<Tile> getTilesByBoardId(Integer boardId);

    /**
     *
     * 任意のtile_idと一致するレコードを返却する
     * @param tile_id
     * @return
     */
    Tile load(Integer tile_id);
}
