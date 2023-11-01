package com.example.demo.data_interfaces;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.data_tables.*;
import java.util.List;


@Repository
public interface UsersRepo extends JpaRepository<Users , String>{
    Users findByUserId(String userId);
    Users findByEmail(String email);
}
