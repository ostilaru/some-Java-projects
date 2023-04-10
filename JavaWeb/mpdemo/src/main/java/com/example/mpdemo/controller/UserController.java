package com.example.mpdemo.controller;

import com.example.mpdemo.entity.User;
import com.example.mpdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user")
    public String query(){

        List<User> List = userMapper.selectList(null);
        System.out.println(List);
        return "查询用户";
    }

    @PostMapping("/user")
    public String insert(User user){
        int i = userMapper.insert(user);
        if(i > 0)
            System.out.println("添加成功");
        else
            System.out.println("添加失败");
        return "添加用户";
    }
}
