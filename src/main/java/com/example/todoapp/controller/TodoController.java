package com.example.todoapp.controller;

import com.example.todoapp.domain.Board;
import com.example.todoapp.domain.User;
import com.example.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @RequestMapping("/read/boards")
    public List<Board> boards() {
        return todoService.findAllBoard(1);
    }

    @RequestMapping("/create/board")
    public Board createBoard(Board board) {
        return todoService.create(board);
    }

    @RequestMapping("/create/user")
    public User createUser(){
        return null;
    }
}
