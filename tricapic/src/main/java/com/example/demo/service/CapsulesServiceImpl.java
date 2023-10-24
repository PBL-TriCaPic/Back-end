package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data_tables.Capsules;

@Service
public class DatabaseService {
    
    //コンストラクタインジェクション
    private CompanionRepo companionRepo;

    @Autowired
    public DatabaseService(CompanionRepo companionRepo) {

        this.companionRepo = companionRepo;
    }

    //追加メゾット一覧
    //Companion Table
    public Long createCapsule(Capsules companion) {
        try{
            Capsules c = companionRepo.save(companion);
            return c.getCompanionId();
        }catch(Exception e){
            return null;
        }
    }
}
