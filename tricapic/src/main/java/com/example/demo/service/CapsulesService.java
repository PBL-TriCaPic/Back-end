package com.example.demo.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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
    public String saveImage(MultipartFile file) {
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadPath = "your/upload/directory"; // 適切なディレクトリパスを設定
            Path filePath = Paths.get(uploadPath).resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // ファイルのメタデータをデータベースに保存するロジックを追加
            Photos photo = new Photos();
            photo.setFileName(fileName);
            photo.setFilePath(filePath.toString());
            imageRepo.save(photo);

            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

