package com.example.todoapp.mapper;

import com.example.todoapp.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> findAllByUserId(int userId);
    Integer create(Board board);
}
