package com.example.todoapp.user;

import com.example.todoapp.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplicationUserMapper {
    void save(ApplicationUser applicationUser);
    ApplicationUser findByUid(String uid);
}
