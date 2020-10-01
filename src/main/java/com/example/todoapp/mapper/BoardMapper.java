package com.example.todoapp.mapper;

import com.example.todoapp.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    /**
     * 任意のユーザーのボードをリストで返す
     *
     * @param user_uid
     * @return 任意のユーザーのボード一覧を返す
     */
    List<Board> findAllByUserId(String user_uid);

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

    /**
     * ボードを削除する
     *
     * @param board_id
     */
    void delete(Integer board_id);
}
