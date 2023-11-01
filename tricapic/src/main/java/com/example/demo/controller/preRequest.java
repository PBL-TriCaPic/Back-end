package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data_functions.DataOperation;
import com.example.demo.data_tables.Capsules;
import com.example.demo.data_tables.Users;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

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

    @PostMapping("/login")
    public pre userLogin(@RequestBody Users userInf){
        Users users = dataOperation.readUser(userInf.getEmail());
        if (users == null){
            // ユーザーが存在しないとき
            throw new IllegalArgumentException("ユーザーが見つかりませんでした：" + userInf.getEmail());
        }
        String pass = userInf.getPassword();
        String dbpass = users.getPassword();
        System.out.println(pass+dbpass);
        
        pre result = new pre();

        if (pass.equals(dbpass)) {
            result.setUserId(users.getUserId());
            result.setUsername(users.getName());
            
            // カプセル情報
            List<Capsules> capsulesList = dataOperation.getCapsulesInfoByUserId(users.getUserId());

            if (!capsulesList.isEmpty()) {
            List<Long> capsulesId = new ArrayList<>();
            List<Float> capsuleLat = new ArrayList<>();
            List<Float> capsuleLon = new ArrayList<>();

            for (Capsules capsules : capsulesList) {
                capsulesId.add(capsules.getCapsulesId());
                capsuleLat.add(capsules.getCapsuleLat());
                capsuleLon.add(capsules.getCapsuleLon());
            }
            result.setCapsulesIdList(capsulesId);
            result.setCapsuleLatList(capsuleLat);
            result.setCapsuleLonList(capsuleLon);
        }
    } else {
        result.setUserId("false");
    }
    return result;
}
    
}



@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class pre{
    String userId;
    String username;
    List<Long> capsulesIdList;
    List<Float> capsuleLatList;
    List<Float> capsuleLonList;

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setCapsulesIdList(List<Long> capsulesIdList) {
        this.capsulesIdList = capsulesIdList;
    }

    public void setCapsuleLatList(List<Float> capsuleLatList) {
        this.capsuleLatList = capsuleLatList;
    }

    public void setCapsuleLonList(List<Float> capsuleLonList) {
        this.capsuleLonList = capsuleLonList;
    }
    
}
