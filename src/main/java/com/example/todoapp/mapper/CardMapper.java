package com.example.todoapp.mapper;

import com.example.todoapp.domain.Card;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CardMapper {
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
}
