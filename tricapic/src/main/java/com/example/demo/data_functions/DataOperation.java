package com.example.demo.data_functions;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.data_interfaces.*;
import com.example.demo.data_tables.*;


@Service
public class DataOperation {

    //コンストラクタインジェクション
    private UsersRepo usersRepo;
    private CapsulesRepo capsulesRepo;

    public DataOperation(UsersRepo usersRepo, CapsulesRepo capsulesRepo) {
        this.usersRepo = usersRepo;
        this.capsulesRepo = capsulesRepo;
    }

    //下記に必要なデータベース操作を記述

    //Add new data for Users table
    public boolean createUser(Users newUser) {
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty() 
        || newUser.getUserId() == null || newUser.getUserId().isEmpty()
        || newUser.getPassword() == null || newUser.getPassword().isEmpty()
        || newUser.getName() == null || newUser.getName().isEmpty()) {
            return false;
        }
        
        
        try {
            // 1. データベース内でUserIdが存在するか確認
            Users existingUser = usersRepo.findByUserId(newUser.getUserId());

            if (existingUser != null) {
                // 2. UserIdが既に存在する場合、エラーを処理するかエラーメッセージを返す
                return false; // 例えば、すでに存在するユーザーの場合はfalseを返す
            } else {
                // 3. UserIdが存在しない場合、新しいユーザーをデータベースに挿入
                usersRepo.save(newUser);
                return true;
            }
        
        } catch(Exception e) {
            return false;
        }
    }

    //Get user data by email
    public Users readUser(String email) {
        return usersRepo.findByEmail(email);
    }

    // ユーザーIDに基づいてカプセル情報を取得
    public List<Capsules> getCapsulesInfoByUserId(String userId) {
        return capsulesRepo.findByUsers_UserId(userId);
}

}
