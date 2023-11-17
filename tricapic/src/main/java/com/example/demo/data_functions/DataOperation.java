package com.example.demo.data_functions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.example.demo.data_interfaces.*;
import com.example.demo.data_tables.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import org.apache.commons.codec.binary.Base64;


@Service
public class DataOperation {

    //コンストラクタインジェクション
    private UsersRepo usersRepo;
    private CapsulesRepo capsulesRepo;
    private PhotosRepo photosRepo;

    public DataOperation(UsersRepo usersRepo, CapsulesRepo capsulesRepo
    , PhotosRepo photosRepo) {
        this.usersRepo = usersRepo;
        this.capsulesRepo = capsulesRepo;
        this.photosRepo = photosRepo;
    }

    //下記に必要なデータベース操作を記述

    //Add new data for Users table
    public boolean createUser(Users newUser) {
        if (newUser.getEmail() == null || newUser.getEmail().isEmpty() 
        || newUser.getUserId() == null || newUser.getUserId().isEmpty()
        || newUser.getPassword() == null || newUser.getPassword().isEmpty()
        || newUser.getName() == null || newUser.getName().isEmpty()) {
            return false;
        }
        
        
        try {
            // 1. データベース内でUserIdが存在するか確認
            Users existingUser = usersRepo.findByUserId(newUser.getUserId());

            if (existingUser != null) {
                // 2. UserIdが既に存在する場合、エラーを処理するかエラーメッセージを返す
                return false; // 例えば、すでに存在するユーザーの場合はfalseを返す
            } else {
                // 3. UserIdが存在しない場合、新しいユーザーをデータベースに挿入
                usersRepo.save(newUser);
                return true;
            }
        
        } catch(Exception e) {
            return false;
        }
    }

    //Get user data by email
    public Users readUser(String email) {
        return usersRepo.findByEmail(email);
    }

    // ユーザーIDに基づいてカプセル情報を取得
    public List<Capsules> getCapsulesInfoByUserId(String userId) {
        return capsulesRepo.findByUsers_UserId(userId);
}


    // カプセルIDからカプセル情報を取得
    public Capsules getCapsuleInfo(Long capsulesId) {
        return capsulesRepo.findById(capsulesId).orElse(null);
}

    // get image data inf
    public List<Photos> getPhotosInf(Long capsuleId) {
        return photosRepo.findByCapsules_CapsulesId(capsuleId);
    }

    //
    public String getImageDataAsBase64(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            System.out.println(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageData = baos.toByteArray();
            return Base64.encodeBase64String(imageData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
