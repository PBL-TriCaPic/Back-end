package com.example.demo.data_interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.data_tables.Relations;
import com.example.demo.data_tables.Users;
public interface RelationsRepo extends JpaRepository<Relations, Long>{
        
    // フォローとフォロワーでレコードを探す
    Relations findByFollowerIdAndFollowedId(Users followerId, Users followedId);

    @Query("SELECT r FROM Relations r WHERE r.followerId.userId = :followerId AND r.followedId.userId = :followedId")
    Relations findByFollowerIdAndFollowedId(@Param("followerId") String user1Id, @Param("followedId") String user2Id);

    // ユーザーのフォロー数の照会
    @Query(value = "SELECT COUNT(*) FROM Relations WHERE follower_id = ?1", nativeQuery = true)
    Long countByFollowerId(String followerUser);

    // ユーザーのフォロワー数の照会
    @Query(value = "SELECT COUNT(*) FROM Relations WHERE followed_id = ?1", nativeQuery = true)
    Long countByFollowedId(String followedUser);

    // ユーザーのフォローリストの照会
    @Query("SELECT r.followedId.userId FROM Relations r WHERE r.followerId.userId = :userId")
    List<String> findFollowerIdByUserId(@Param("userId") String userId);

    // ユーザーのフォロワーリストの照会
    @Query("SELECT r.followerId.userId FROM Relations r WHERE r.followedId.userId = :userId")
    List<String> findFollowedIdByUserId(@Param("userId") String userId);
}