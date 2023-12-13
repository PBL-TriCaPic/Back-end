package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data_tables.Capsules;
import com.example.demo.data_tables.Users;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}") //エンドポイントもっと複雑に
public CapsuleUser getUserWithCapsules(@PathVariable String userId) {
    Users user = userService.getUserById(userId);
    List<Capsules> capsules = userService.getCapsulesByUserId(userId);

    CapsuleUser capsuleUser = new CapsuleUser();//インスタンス

    capsuleUser.setIconImage(user.getIconImage());
    capsuleUser.setProfile(user.getProfile());
    capsuleUser.setName(user.getName());

    List<String> capsuleIds = capsules.stream().map(capsule -> String.valueOf(capsule.getCapsulesId())).collect(Collectors.toList());
    
    capsuleUser.setCapsulesId(capsuleIds);

    return capsuleUser;
}


    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    class CapsuleUser {

        private String iconImage;
        private String profile;
        private String name;
        private List<String> capsulesId;

            // iconImageのゲッター
        public String getIconImage() {
            return iconImage;
        }

        // iconImageのセッター
        public void setIconImage(String iconImage) {
            this.iconImage = iconImage;
        }

        // profileのゲッター
        public String getProfile() {
            return profile;
        }

        // profileのセッター
        public void setProfile(String profile) {
            this.profile = profile;
        }

        // nameのゲッター
        public String getName() {
            return name;
        }

        // nameのセッター
        public void setName(String name) {
            this.name = name;
        }

        // capsulesIdのゲッター
        public List<String> getCapsulesId() {
            return capsulesId;
        }

        // capsulesIdのセッター
        public void setCapsulesId(List<String> capsulesId) {
            this.capsulesId = capsulesId;
        }
    }
}
