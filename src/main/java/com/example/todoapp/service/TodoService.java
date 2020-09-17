package com.example.todoapp.service;

import com.example.todoapp.domain.Board;
import com.example.todoapp.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TodoService {
    @Autowired
    private BoardMapper boardMapper;

    public List<Board> findAllBoard(Integer userId) {
        return boardMapper.findAllByUserId(userId);
    }

    public Board create(Board board) {
        Integer id = boardMapper.create(board);
        board.setBoard_id(id.toString());
        return board;
    }
}
