package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RequestMapping("redis")
@RestController
public class RedisDemoController {


    @Resource(name = "userServiceRedis")
    private  UserService userService;

    // http://localhost:8080/redis/delete/1
    @GetMapping("delete/{userId}")
    public String delete(@PathVariable("userId") String userId){
        userService.delete(userId);
        return "删除成功";
    }

    // http://localhost:8080/redis/get/1
    @GetMapping("get/{userId}")
    public User get(@PathVariable("userId") String userId){
        User user = userService.selectById(userId);
        return user;
    }


    // http://localhost:8080/redis/add/1
    @GetMapping("add/{userId}")
    public String add(@PathVariable("userId") String userId){

        if (userService.isExist(userId))
            return "userId已经存在，请换一个。";

        User user = new User();
        user.setUserId(userId);
        user.setUserName("Name-" + userId);
        user.setPhone("186001" + userId);
        user.setAge(10);
        userService.save(user);

        return "添加了" + user + "成功";
    }

}
