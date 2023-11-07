package com.example.demo.data_interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data_tables.Photos;


@Repository
public interface ImageRepo extends JpaRepository<Photos, Long> {
    // 任意のカスタムメソッドが必要な場合、ここに追加

    // ファイル名に基づいて写真を検索するカスタムメソッド
    //List<Photos> findByFileName(String fileName);

    // ファイルパスに基づいて写真を検索するカスタムメソッド
    //Photos findByFilePath(String filePath);

    //上の追加するとエラーでdockerのspringbootが動かなくなる

}
