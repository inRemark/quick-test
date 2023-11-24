package com.example;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RequestMapping("user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("list")
    public List<User> list(){
        List<User> userList = userService.selectList(null);
        return userList;
    }


    // http://localhost:8080/user/get/1
    @GetMapping("get/{userId}")
    public User get(@PathVariable("userId") Long userId){

        // userService.findUserTr();
//        User user = userService.selectById(userId);
//        User user = userService.selectByIdTr(userId);
        User user = userService.selectUserByIdOut(userId);

        System.out.println(">>info:" + user.getUserId() + ":" + user.getUserName());
        try {
            System.out.println(">>阻塞:" + userId);
            // 耗时业务
            Thread.sleep(120000);
            System.out.println(">>通行:"  + userId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // http://localhost:8080/user/add/100
    @GetMapping("add/{count}")
    public String add(@PathVariable("count") Integer count){

        for (int i = 0; i< count; i++){
            User user = new User();
            user.setUserName("Name-" + i);
            user.setPhone("186001" + i);
            user.setAge(count);

            userService.insert(user);
        }
        return "添加了" + count + "个用户";
    }



}
