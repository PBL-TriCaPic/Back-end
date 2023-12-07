package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data_interfaces.UsersRepo;
import com.example.demo.data_tables.Users;

@Service
public class UserService {

    private final UsersRepo usersRepo;

    @Autowired
    public UserService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public Users getUserById(String userId) {
        return usersRepo.findByUserId(userId);
    }
}
