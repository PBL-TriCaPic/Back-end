package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long> createCapsule(@RequestBody CapsuleRequest capsuleRequest) {
    // カプセル情報を取得
    Capsules newCapsule = new Capsules();
    newCapsule.setCapsuleDate(LocalDateTime.now());
    newCapsule.setCapsuleLat(capsuleRequest.getCapsuleLat());
    newCapsule.setCapsuleLon(capsuleRequest.getCapsuleLon());
    newCapsule.setTextData(capsuleRequest.getTextData());

    // 画像データのBase64エンコードを取得
    String imageDataBase64 = capsuleRequest.getImageDataBase64();

    // 新しいカプセルをデータベースに保存
    Long savedCapsuleId = dataOperation.createCapsule(newCapsule, imageDataBase64);

    if (savedCapsuleId != null) {
        return new ResponseEntity<>(savedCapsuleId, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    

     // カプセル情報参照(CapsuleDate, Text, ReopenDate のみ)
        @GetMapping("/get/capsules/{capsulesId}")
        public CapsuleInfo getCapsuleInfo(@PathVariable Long capsulesId) {
            Capsules capsule = dataOperation.getCapsuleInfo(capsulesId);
            if (capsule != null) {
                CapsuleInfo result = new CapsuleInfo();
                result.setCapsuleDate(capsule.getCapsuleDate());
                result.setTextData(capsule.getTextData());
                // result.setPinColor(capsule.getPinColor());
                // result.setPinForm(capsule.getPinForm());
                // result.setPassword(capsule.getPassword());
                result.setReopenDate(capsule.getReopenDate());
                // result.setVideoData(capsule.getVideoData());
                // result.setAudioData(capsule.getAudioData());
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

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class CapsuleRequest {
    private LocalDateTime capsuleDate;
    private Float capsuleLat;
    private Float capsuleLon;
    private String textData;
    private String imageDataBase64;


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

    // public void setVideoData(String videoData) {
    //     this.videoData = videoData;
    // }

    // public void setAudioData(String audioData) {
    //     this.audioData = audioData;
    // }
}


