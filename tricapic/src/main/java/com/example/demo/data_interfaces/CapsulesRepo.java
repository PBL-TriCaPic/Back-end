package com.example.demo.data_interfaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data_tables.Capsules;

// public interface CapsulesRepo {
//     CapsulesRepo createCapsule(CapsulesRepo capsule);
//     CapsulesRepo getCapsule(Long id);
// }

@Repository
public interface CapsulesRepo extends JpaRepository<Capsules , Long>{
    Capsules findBycapsulesId(Long capsulesId);
}