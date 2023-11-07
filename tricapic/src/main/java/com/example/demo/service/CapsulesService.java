package com.example.demo.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.data_interfaces.CapsulesRepo;
import com.example.demo.data_interfaces.ImageRepo;
import com.example.demo.data_tables.Capsules;
import com.example.demo.data_tables.Photos;


@Service
public class CapsulesService {

    private final CapsulesRepo capsulesRepo;
    private final ImageRepo imageRepo; // ImageRepoを追加

    //@Autowired
    public CapsulesService(CapsulesRepo capsulesRepo, ImageRepo imageRepo) {
        this.capsulesRepo = capsulesRepo;
        this.imageRepo = imageRepo; // ImageRepoを初期化
    }

    // create capsules
    public boolean createCapsule(Capsules capsule) {
        try {
            capsulesRepo.save(capsule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // get capsules
    public boolean getCapsule(Long id) {
        return capsulesRepo.findById(id).isPresent();
    }

    // save image
    public String saveImage(MultipartFile file) { //MultipartFileインスタンスを使いfileを引数とする
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename()); //fileからオリジナルのファイル名を取得しStringUtils.cleanPath() メソッドで安全なファイル名を取得
            String uploadPath = "C:\\Users\\2021052\\PBL\\Upload"; // 適切なディレクトリパスを設定、アップロード先のディレクトリだと思う
            
            //Path filePath = Paths.get(uploadPath).resolve(fileName);
            Path filePath = Paths.get(uploadPath, fileName);
            //Path filePath = Paths.get(uploadPath, fileName.replace("/", "\\"));

            
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // ファイルのメタデータをデータベースに保存
            Photos photo = new Photos(); //Photosインスタンス化
            photo.setImageData(filePath.toString()); // ファイルパスを設定
            imageRepo.save(photo);

            return filePath.toString(); // 保存されたファイルのフルパスを文字列で返す
        } catch (Exception e) {
            e.printStackTrace(); //エラー情報をコンソールに出力
            return null;
        }
    }
}

