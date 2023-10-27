package com.example.demo.data_functions;


import com.example.demo.data_interfaces.*;
import com.example.demo.data_tables.*;
import org.springframework.stereotype.Service;

@Service
public class DataOperation {

    //コンストラクタインジェクション
    private UsersRepo usersRepo;

    public DataOperation(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    //下記に必要なデータベース操作を記述

    //Add new data for Users table
    public boolean createUser(Users newUser) {
        try {
            usersRepo.save(newUser);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    //Get user data by email
    public Users readUser(String email) {
        return usersRepo.findByEmail(email);
    } 


}
