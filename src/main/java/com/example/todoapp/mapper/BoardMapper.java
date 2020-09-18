package com.example.todoapp.mapper;

import com.example.todoapp.domain.Board;
import com.example.todoapp.domain.Card;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    /**
     * 任意のユーザーのボードをリストで返す
     *
     * @param userId
     * @return 任意のユーザーのボード一覧を返す
     */
    List<Board> findAllByUserId(int userId);

    /**
     * user_idをもとにboardを新規作成する
     *
     * @param board
     */
    void create(Board board);

    /**
     * 新規作成されたboardのidを取得する
     *
     * @return 自動採番されたid
     */
    Integer getLastInsertId();

    /**
     * ボード名を変更する
     *
     * @param board
     */
    void update(Board board);
}
