package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


import com.example.demo.data_tables.*;
import com.example.demo.data_functions.*;

@RestController
@RequestMapping("/api")
public class preRequest {
    @Autowired
    private DataOperation dataOperation;

    @PostMapping("/create/user")
    public boolean addNewUser(@RequestBody Users userInf){
        userInf.setLook(false);
        userInf.setNotice(false);
        return dataOperation.createUser(userInf);
    }


    @PostMapping("/demo")
    public void demo(){
        System.out.println("おめでとう！！");
    }

    // @PostMapping("/login")
    // public String userLogin(@RequestBody Users userInf){
    //     Users users=dataOperation.readUser(userInf.getEmail());
    //     String pass=userInf.getPassword();
    //     String dbpass=users.getPassword();

    //     // pre p= new pre();
    //     if(pass.equals(dbpass)){
    //         return (users.getName());
    //         // p.userId=userInf.getUserId();
    //         // p.username=userInf.getName();
    //     }

    //     return "a";
    // }
    @PostMapping("/login")
    public pre userLogin(@RequestBody Users userInf){
        Users users = dataOperation.readUser(userInf.getEmail());
        String pass = userInf.getPassword();
        String dbpass = users.getPassword();
        System.out.println(pass+dbpass);
        
        pre result = new pre();

        if (pass.equals(dbpass)) {
            result.setUserId(users.getUserId());
            result.setUsername(users.getName());
        }else result.setUserId("false");

        return result;
}

}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class pre{
    String userId;
    String username;
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
}