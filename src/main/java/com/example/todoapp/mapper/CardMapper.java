package com.example.todoapp.mapper;

import com.example.todoapp.domain.Card;
import com.example.todoapp.domain.Tile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardMapper {
    /**
     * tile_idが一致するカードのindicatorの最大値を取得する
     */
    Integer getMaxIndicator(Integer tileId);

    /**
     * tile_idをもとにcardを新規作成する
     *
     * @param card
     */
    void create(Card card);

    /**
     * 新規作成されたcardのidを取得する
     *
     * @return 自動採番されたid
     */
    Integer getLastInsertId();

    /**
     * カード名の編集
     *
     * @param card
     */
    void update(Card card);

    /**
     * カードの削除
     *
     * @param cardId
     */
    void delete(Integer cardId);

    /**
     * 任意のタイルIDを持つ全てのカードを検索する
     * @param tileId
     * @return
     */
    List<Card> getCardsByTileId(Integer tileId);

    /**
     *
     * 任意のtile_idと一致するレコードを返却する
     * @param card_id
     * @return
     */
    Card load(Integer card_id);


}
