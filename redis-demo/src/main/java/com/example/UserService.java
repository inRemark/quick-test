package com.example;

public interface UserService {

    User selectById(String userId);
    boolean isExist(String userId);
    void save(User user);
    void delete(String userId);

}
