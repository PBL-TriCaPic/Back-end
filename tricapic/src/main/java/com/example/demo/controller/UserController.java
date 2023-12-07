package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data_tables.Capsules;
import com.example.demo.data_tables.Users;
import com.example.demo.service.UserCapsuleData; // ここでUserCapsuleDataを正しくインポートする
import com.example.demo.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public UserCapsuleData getUserWithCapsules(@PathVariable String userId) {
        Users user = userService.getUserById(userId);
        List<Capsules> capsules = userService.getCapsulesByUserId(userId);
    
        // UserCapsuleData クラスのインスタンスを作成し、ユーザー情報とカプセル情報を設定します
        UserCapsuleData userCapsuleData = new UserCapsuleData(user, capsules);
    
        // UserCapsuleData インスタンスを返します
        return userCapsuleData;
    }
    
}
