package com.example.demo.data_interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.data_tables.Relations;
import com.example.demo.data_tables.Users;
public interface RelationsRepo extends JpaRepository<Relations, Long>{
        
    // フォローとフォロワーでレコードを探す
    Relations findByFollowerAndFollowed(Users followerId, Users followedId);

    // 現在ログインしているユーザーのフォローしているユーザー数の照会
    @Query(value = "SELECT COUNT(*) FROM Relations WHERE followerId = ?1", nativeQuery = true)
    Long countByFollower(Users followerUser);

    // 現在ログインしているユーザーをフォローしているユーザー数の照会
    @Query(value = "SELECT COUNT(*) FROM Relations WHERE followedId = ?1", nativeQuery = true)
    Long countByFollowed(Users followedUser);


    // 現在ログインしているユーザーのフォローアップリストの照会
    @Query("SELECT r.followedId FROM Relations r WHERE r.followerId = :userId")
    List<String> findFollowerByUserId(@Param("userId") String userId);

    // 現在ログインしているユーザーのフォロワー一覧を照会
    @Query("SELECT r.followerId FROM Relations r WHERE r.followedId = :userId")
    List<String> findFollowedByUserId(@Param("userId") String userId);
}
