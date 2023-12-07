package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data_interfaces.CapsulesRepo;
import com.example.demo.data_interfaces.UsersRepo;
import com.example.demo.data_tables.Capsules;
import com.example.demo.data_tables.Users;

@Service
public class UserService {

    private final UsersRepo usersRepo;
    private final CapsulesRepo capsulesRepo;

    @Autowired
    public UserService(UsersRepo usersRepo, CapsulesRepo capsulesRepo) {
        this.usersRepo = usersRepo;
        this.capsulesRepo = capsulesRepo;
    }

    public Users getUserById(String userId) {
        return usersRepo.findByUserId(userId);//UsersRepoで探してくれる
    }

    public List<Capsules> getCapsulesByUserId(String userId) {
        return capsulesRepo.findByUsers_UserId(userId);
    }

    public List<Capsules> getCapsulesByUserId(String userId) {
        return capsulesRepo.findByUsers_UserId(userId);
    }
}
