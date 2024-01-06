package com.example.demo.data_functions;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data_interfaces.CapsulesRepo;
import com.example.demo.data_interfaces.PhotosRepo;
import com.example.demo.data_interfaces.UsersRepo;
import com.example.demo.data_tables.Capsules;
import com.example.demo.data_tables.Photos;
import com.example.demo.data_tables.Users;


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

    // カプセルテーブルに情報追加
    public Long createCapsule(Capsules newCapsule, String imageDataBase64) {
        // if (newCapsule.getCapsuleDate() == null
        // || newCapsule.getCapsulesId() == null
        // || Float.isNaN(newCapsule.getCapsuleLat())
        // || Float.isNaN(newCapsule.getCapsuleLon())) {
        //     return null; // 必要なデータが不足している場合はnullを返す
        // }

        try {

            // // ユーザーが存在するか確認
            // Users user = newCapsule.getUsers();
            // if (user == null || usersRepo.findById(user.getUserId()).orElse(null) == null) {
            //     return null;
            // }

            // カプセルの作成日時を現在時刻に設定
            newCapsule.setCapsuleDate(LocalDateTime.now(ZoneId.of("Asia/Tokyo")));

            // 新しいカプセルをデータベースに保存
            Capsules savedCapsule = capsulesRepo.save(newCapsule);

            // 画像データをデコードしてファイルに保存
            String path = saveImageData(savedCapsule.getCapsulesId(), imageDataBase64);
            Photos newpPhotos= new Photos();
            newpPhotos.setCapsules(savedCapsule);
            newpPhotos.setImageData(path);
            photosRepo.save(newpPhotos);
            
            // 保存後のカプセルIDを取得して返す
            return savedCapsule.getCapsulesId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 画像データをデコードしてファイルに保存
    private String saveImageData(Long capsulesId, String imageDataBase64) {
        try {
            byte[] imageData = Base64.decodeBase64(imageDataBase64);

            // ファイルパスの設定
            String filePath = "/tricapic/src/main/java/com/example/demo/data_functions/image" + capsulesId + ".jpg";

            // 画像データをファイルに保存
            Files.write(Paths.get(filePath), imageData);
            

            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
            // System.out.println("今ここ！！");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageData = baos.toByteArray();
            return Base64.encodeBase64String(imageData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public boolean updateUserINF(Users inputUsers, String imageDataBase64){
        Users updateInf = usersRepo.findByUserId(inputUsers.getUserId());
        if(updateInf != null){
            String profImage_path;
            try {
                byte[] imageData = Base64.decodeBase64(imageDataBase64);
                profImage_path = "/tricapic/src/main/java/com/example/demo/image/prof/image_"+updateInf.getUserId()+".jpg";
                Files.write(Paths.get(profImage_path), imageData);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            updateInf.setIconImage(profImage_path);
            updateInf.setName(inputUsers.getName());
            updateInf.setProfile(inputUsers.getProfile());
            usersRepo.save(updateInf);
            return true;
        }
        return false;
    }

    public String getUserIconByUserId(String userId) {
        try {
            Users user = usersRepo.findByUserId(userId);
            if (user != null && user.getIconImage() != null) {
                String imagePath = user.getIconImage();
                return getImageDataAsBase64(imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
