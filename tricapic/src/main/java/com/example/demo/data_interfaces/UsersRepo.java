package com.example.demo.data_interfaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.data_tables.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users , String>{
    Users findByUserId(String userId);
    Users findByEmail(String email);

    @Query("SELECT u.name FROM Users u WHERE u.userId = :userId")
    String findNameByUserId(@Param("userId") String userId);
}
