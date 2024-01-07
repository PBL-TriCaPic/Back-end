package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data_functions.DataOperation;
import com.example.demo.data_tables.Capsules;
import com.example.demo.data_tables.Photos;
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

            // アイコンがnullのときはnullを格納
            if (users.getIconImage() != null|| !users.getIconImage().isEmpty()) {
                System.out.println("アイコン画像あり");
                String iconimg = dataOperation.getImageDataAsBase64(users.getIconImage());
                result.setIconImage(iconimg);
            } else {
                result.setIconImage(null);
            }

            // プロフィールがnullのときはnullを格納
            if (users.getProfile() != null || !users.getProfile().isEmpty()) {
                result.setProfile(users.getProfile());
            } else {
                result.setProfile(null);
            }
            
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


    @PostMapping("/create/capsule")
    public pre2 createCapsule(@RequestBody CapsuleRequest capsuleRequest) {
    // カプセル情報を取得
    Users users = new Users();
    Capsules newCapsule = new Capsules();
    users.setUserId(capsuleRequest.getUserId());
    newCapsule.setUsers(users);
    newCapsule.setCapsuleDate(LocalDateTime.now());
    newCapsule.setCapsuleLat(capsuleRequest.getCapsuleLat());
    newCapsule.setCapsuleLon(capsuleRequest.getCapsuleLon());
    newCapsule.setTextData(capsuleRequest.getTextData());

    // 画像データのBase64エンコードを取得
    String imageDataBase64 = capsuleRequest.getImageDataBase64();

    // 新しいカプセルをデータベースに保存
    dataOperation.createCapsule(newCapsule, imageDataBase64);

    pre2 result2 = new pre2();

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
        result2.setCapsulesIdList(capsulesId);
        result2.setCapsuleLatList(capsuleLat);
        result2.setCapsuleLonList(capsuleLon);
    }
    return result2;
}


    

     // カプセル情報参照(CapsuleDate, Text, ReopenDate のみ)
    @GetMapping("/get/capsules/{capsulesId}")
    public CapsuleInfo getCapsuleInfo(@PathVariable Long capsulesId) {
        Capsules capsule = dataOperation.getCapsuleInfo(capsulesId);
        if (capsule != null) {
            CapsuleInfo result = new CapsuleInfo();
            // アイコンがnullのときはnullを格納
            if (capsule.getUsers().getIconImage() != null || !capsule.getUsers().getIconImage().isEmpty()) {
                String iconimg = dataOperation.getImageDataAsBase64(capsule.getUsers().getIconImage());
                result.setIconImage(iconimg);
            } else {
                result.setIconImage(null);
            }
            result.setCapsuleDate(capsule.getCapsuleDate());
            result.setTextData(capsule.getTextData());
            result.setReopenDate(capsule.getReopenDate());

            // 画像データをBase64エンコードして設定
            String imageData = null;
            List<Photos> photoinfList = dataOperation.getPhotosInf(capsule.getCapsulesId());
            for (Photos photosinf : photoinfList) {
                imageData = dataOperation.getImageDataAsBase64(photosinf.getImageData());
            }

            result.setImageData(imageData);

            return result;
        }
        return null;
    }

    @PostMapping("/update/userInf")
    public boolean updateUserInf(@RequestBody Users newuserInf){
        String iconImage = newuserInf.getIconImage();
        newuserInf.setIconImage(null);
        return dataOperation.updateUserINF(newuserInf, iconImage);
    }
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class pre2 {
    List<Long> capsulesIdList;
    List<Float> capsuleLatList;
    List<Float> capsuleLonList;


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

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class pre{
    String userId;
    String username;
    String iconImage;
    String profile;
    List<Long> capsulesIdList;
    List<Float> capsuleLatList;
    List<Float> capsuleLonList;

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }
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

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class CapsuleRequest {
    private String userId;
    private LocalDateTime capsuleDate;
    private Float capsuleLat;
    private Float capsuleLon;
    private String textData;
    private String imageDataBase64;


        public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCapsuleDate() {
        return capsuleDate;
    }

    public void setCapsuleDate(LocalDateTime capsuleDate) {
        this.capsuleDate = capsuleDate;
    }

    public float getCapsuleLat() {
        return capsuleLat;
    }

    public void setCapsuleLat(Float capsuleLat) {
        this.capsuleLat = capsuleLat;
    }

    public float getCapsuleLon() {
        return capsuleLon;
    }

    public void setCapsuleLon(Float capsuleLon) {
        this.capsuleLon = capsuleLon;
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public String getImageDataBase64() {
        return imageDataBase64;
    }

    public void setImageDataBase64(String imageDataBase64) {
        this.imageDataBase64 = imageDataBase64;
    }

}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class CapsuleInfo {
    String iconImage;
    LocalDateTime capsuleDate;
    String textData;
    String imageData;

    // String pinColor;
    // String pinForm;
    // String password;
    LocalDateTime reopenDate;
    // String videoData;
    // String audioData;

    public void setCapsuleDate(LocalDateTime capsuleDate) {
        this.capsuleDate = capsuleDate;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public void setImageData(String imagedata) {
        this.imageData = imagedata;
    }

    // public void setPinColor(String pinColor) {
    //     this.pinColor = pinColor;
    // }

    // public void setPinForm(String pinForm) {
    //     this.pinForm = pinForm;
    // }

    // public void setPassword(String password) {
    //     this.password = password;
    // }

    public void setReopenDate(LocalDateTime reopenDate) {
        this.reopenDate = reopenDate;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    // public void setVideoData(String videoData) {
    //     this.videoData = videoData;
    // }

    // public void setAudioData(String audioData) {
    //     this.audioData = audioData;
    // }
}


