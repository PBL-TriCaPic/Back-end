package com.example.demo.data_interfaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data_tables.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users , String>{
    Users findByEmail(String email);
    Users findByUserId(String userid);
}
