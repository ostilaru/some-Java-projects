package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

public class UserController {

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id){
        System.out.println("id = " + id);
        return "根据id获取用户信息";
    }

    @PostMapping("/user")
    public String addUser(){
        return "添加用户";
    }

    @PutMapping("/user/{id}")
    public String updateUser(@PathVariable int id){
        System.out.println("id = " + id);
        return "更新用户信息";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable int id){
        System.out.println("id = " + id);
        return "删除用户";
    }
}
