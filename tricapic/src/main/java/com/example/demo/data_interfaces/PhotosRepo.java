package com.example.demo.data_interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data_tables.Photos;

@Repository
public interface PhotosRepo extends JpaRepository<Photos, Long>{
    List<Photos> findByCapsules_CapsulesId(Long capsulesId);
}
