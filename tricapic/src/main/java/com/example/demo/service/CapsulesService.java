package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Image;
import com.example.demo.data_interfaces.CapsulesRepo;
import com.example.demo.data_interfaces.ImageRepo;
import com.example.demo.data_tables.Capsules;

@Service
public class CapsulesService {

    private final CapsulesRepo capsulesRepo;
    private final ImageRepo imageRepo;

    @Autowired
    public CapsulesService(CapsulesRepo capsulesRepo) {
        this.capsulesRepo = capsulesRepo;
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
     public boolean saveImage(byte[] imageData) {
        try {
            Image image = new Image();
            image.setImageData(imageData);
            ImageRepo.save(image); // imageRepo を介してデータベースに保存
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
