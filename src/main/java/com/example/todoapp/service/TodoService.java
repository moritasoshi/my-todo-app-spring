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
        board.setBoard_id(id.toString());
        return board;
    }

    public Tile create(Tile tile) {
        tileMapper.create(tile);
        Integer id = tileMapper.getLastInsertId();
        tile.setTile_id(id.toString());
        return tile;
    }
    public Card create(Card card) {
        cardMapper.create(card);
        Integer id = cardMapper.getLastInsertId();
        card.setTile_id(id.toString());
        return card;
    }
}
