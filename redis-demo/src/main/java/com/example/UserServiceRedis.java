package com.example;

import com.example.redis.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service(value = "userServiceRedis")
public class UserServiceRedis implements UserService{

    @Resource
    private RedisUtils redisUtils;

    @Override
    public User selectById(String userId) {
        User user = redisUtils.get(userId, User.class);
        return user;
    }

    @Override
    public boolean isExist(String userId){
        boolean r = redisUtils.hasKey(userId);
        return r;
    }

    @Override
    public void save(User user) {
        user.setCreateTime(new Date());
        redisUtils.set(String.valueOf(user.getUserId()), user);
    }

    @Override
    public void delete(String userId) {
        redisUtils.delete(userId);
    }

}
