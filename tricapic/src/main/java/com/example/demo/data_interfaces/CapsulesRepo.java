package com.example.demo.data_interfaces;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data_tables.Capsules;

// public interface CapsulesRepo {
//     CapsulesRepo createCapsule(CapsulesRepo capsule);
//     CapsulesRepo getCapsule(Long id);
// }

@Repository
public interface CapsulesRepo extends JpaRepository<Capsules, Long> {
    List<Capsules> findByUsers_UserId(String userId);
}

