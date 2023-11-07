package com.example.demo.data_interfaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data_tables.Capsules;

@Repository
public interface CapsulesRepo extends JpaRepository<Capsules, Long> {
    // 任意のカスタムメソッドが必要な場合、ここに追加
}


