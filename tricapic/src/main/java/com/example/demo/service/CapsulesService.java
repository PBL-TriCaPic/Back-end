package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data_interfaces.CapsulesRepo;
import com.example.demo.data_tables.Capsules;

@Service
public class CapsulesService {

    private final CapsulesRepo capsulesRepo;

    @Autowired
    public CapsulesService(CapsulesRepo capsulesRepo) {
        this.capsulesRepo = capsulesRepo;
    }

    //create capsules
    public Capsules createCapsule(Capsules capsule) {
        return capsulesRepo.save(capsule);
    }

    //get capsules
    public Capsules getCapsule(Long id) {
        return capsulesRepo.findById(id).orElse(null);
    }
}
