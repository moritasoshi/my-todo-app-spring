package com.example.todoapp.service;

import com.example.todoapp.domain.Board;
import com.example.todoapp.domain.Card;
import com.example.todoapp.domain.Tile;
import com.example.todoapp.mapper.BoardMapper;
import com.example.todoapp.mapper.CardMapper;
import com.example.todoapp.mapper.TileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TodoService {
    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private TileMapper tileMapper;
    @Autowired
    private CardMapper cardMapper;

    public List<Board> findAllBoard(Integer userId) {
        return boardMapper.findAllByUserId(userId);
    }

    public Board create(Board board) {
        boardMapper.create(board);
        Integer id = boardMapper.getLastInsertId();
        board.setBoard_id(id);
        return board;
    }

    public Tile create(Tile tile) {
        tileMapper.create(tile);
        Integer id = tileMapper.getLastInsertId();
        tile.setTile_id(id);
        return tile;
    }

    public Card create(Card card) {
        cardMapper.create(card);
        Integer id = cardMapper.getLastInsertId();
        card.setCard_id(id);
        return card;
    }

    public Board update(Board board) {
        boardMapper.update(board);
        return board;
    }

    public Tile update(Tile tile) {
        tileMapper.update(tile);
        return tile;
    }

    public Card update(Card card) {
        cardMapper.update(card);
        return card;
    }

    public Board delete(Board board) {
        // delete board
        boardMapper.delete(board);
        // delete tiles
        board.getTiles()
                .forEach(tile -> tileMapper.delete(tile));
        // delete cards
        board.getTiles().stream()
                .flatMap(tile -> tile.getCards().stream()) // 全ての"tile"からカードリストを取得し新たなList<Card>を作成
                .forEach(card -> cardMapper.delete(card));
        return board;
    }

    public Tile delete(Tile tile) {
        // delete tiles
        tileMapper.delete(tile);
        // delete cards
        tile.getCards()
                .forEach(card -> cardMapper.delete(card));
        return tile;
    }

    public Card delete(Card card) {
        cardMapper.delete(card);
        return card;
    }
}
