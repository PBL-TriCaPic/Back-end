package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data_tables.Capsules;
import com.example.demo.data_tables.Users;
import com.example.demo.service.RelationsService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;




@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final RelationsService relationsService;

    @Autowired
    public UserController(UserService userService, RelationsService relationsService) {
        this.userService = userService;
        this.relationsService = relationsService;
    }
    @GetMapping("/users/{userId}") //エンドポイントもっと複雑に
public CapsuleUser getUserWithCapsules(@PathVariable String userId) {

    Users user = userService.getUserById(userId);

    List<Capsules> capsules = userService.getCapsulesByUserId(userId);

    CapsuleUser capsuleUser = new CapsuleUser();//インスタンス

    capsuleUser.setUserId(user.getUserId());//追加
    capsuleUser.setIconImage(user.getIconImage());
    capsuleUser.setProfile(user.getProfile());
    capsuleUser.setName(user.getName());

    List<String> capsuleIds = capsules.stream().map(capsule -> String.valueOf(capsule.getCapsulesId())).collect(Collectors.toList());
    

    capsuleUser.setCapsulesId(capsuleIds);
    capsuleUser.setCapsulesCount(capsules.size());

    //iconImageはパス名のため、base64でエンコード、デコードを行う。
    String imageDataBase64 = encodeImageToBase64(user.getIconImage());
    capsuleUser.setImageDataBase64(imageDataBase64);

    //フレンドの有無、フレンドの数（舘山くん作プッシュを利用していく）
    int friendsCount = relationsService.getFriendsCount(userId);
    capsuleUser.setFriendsCount(friendsCount);

    return capsuleUser;
}

// 画像をBase64にエンコードするメソッド
private String encodeImageToBase64(String imagePath) {
    if (imagePath == null || imagePath.isEmpty()) {
        return null; // もしくは適切なデフォルト値を返す
    }
    try {
        // 画像ファイルをバイト配列として読み込む
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

        // バイト配列をBase64にエンコードして文字列として返す
        return Base64.encodeBase64String(imageBytes);
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}


    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    class CapsuleUser {
        private String userId;
        private String iconImage;
        private String profile;
        private String name;
        private List<String> capsulesId;
        private int capsulesCount;
        private String imageDataBase64;
        private int friendsCount;

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

        // capsulesCountのゲッター
        public int getCapsulesCount() {
            return capsulesCount;
        }

        // capsulesCountのセッター
        public void setCapsulesCount(int capsulesCount) {
            this.capsulesCount = capsulesCount;
        }

        // imageDataBase64のゲッター
        public String getImageDataBase64() {
            return imageDataBase64;
        }

        // imageDataBase64のセッター
        public void setImageDataBase64(String imageDataBase64) {
            this.imageDataBase64 = imageDataBase64;
        }
        
        //userId関連追加しました
        public String getUserId() {
            return userId;
        }
        
        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getFriendsCount() {
            return friendsCount;
        }

        public void setFriendsCount(int friendsCount) {
            this.friendsCount = friendsCount;
        }
    }
}