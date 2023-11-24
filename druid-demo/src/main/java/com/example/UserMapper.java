package com.example;

import java.util.List;

public interface UserMapper {

    List<User> selectList(User user);
    User selectById(Long userId);

    int insert(User user);
}
