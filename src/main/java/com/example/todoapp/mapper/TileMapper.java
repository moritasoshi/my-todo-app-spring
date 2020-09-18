package com.example.todoapp.mapper;

import com.example.todoapp.domain.Card;
import com.example.todoapp.domain.Tile;
import org.apache.ibatis.annotations.Mapper;

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
     * @param tile
     */
    void delete(Tile tile);
}
