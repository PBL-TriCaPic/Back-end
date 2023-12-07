package com.example.demo.service;

import java.util.List;

import com.example.demo.data_tables.Capsules;
import com.example.demo.data_tables.Users;

public class UserCapsuleData {
    private Users user;
    private List<Capsules> capsules;

    public UserCapsuleData(Users user, List<Capsules> capsules) {
        this.user = user;
        this.capsules = capsules;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Capsules> getCapsules() {
        return capsules;
    }

    public void setCapsules(List<Capsules> capsules) {
        this.capsules = capsules;
    }
}
