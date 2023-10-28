package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data_interfaces.CapsulesRepo;
import com.example.demo.data_tables.Capsules;

@Service
public class CapsulesService {

    
    private final CapsulesRepo capsulesRepo;

    
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
}
