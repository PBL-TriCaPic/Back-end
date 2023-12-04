package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data_tables.Users;
import com.example.demo.service.RelationsService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@RestController
@RequestMapping("/api/relations")
public class RelationsController {

    private final RelationsService relationsService;

    public RelationsController(RelationsService relationsService) {
        this.relationsService = relationsService;
    }

    // フォロー関係を追加するエンドポイント
    @PostMapping("/follow")
    public ResponseEntity<Boolean> addFollow(@RequestBody RelationsRequest relationsrequest) {
        Users followerId = relationsrequest.getFollowerId();
        Users followedId = relationsrequest.getFollowedId();

        if (followerId != null && followedId != null && followerId != followedId) {
            boolean success = relationsService.addFollow(followerId, followedId);
            return ResponseEntity.ok(success);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }

    // フォロー関係を削除するエンドポイント
    @DeleteMapping("/unfollow")
    public ResponseEntity<Boolean> deleteFollow(@RequestBody RelationsRequest relationsrequest) {
        Users followerId = relationsrequest.getFollowerId();
        Users followedId = relationsrequest.getFollowedId();

        if (followerId != null && followedId != null){
            boolean success = relationsService.deleteFollow(followerId, followedId);
            return ResponseEntity.ok(success);
        } else {
            return ResponseEntity.badRequest().body(false);
        }
    }
    
    // フォロー数を取得するエンドポイント
    @GetMapping("/get/follower-count/{userId}")
    public Long getFollowerCount(@PathVariable String userId) {
        return relationsService.getFollowerCount(userId);
    }

    // フォロワー数を取得するエンドポイント
    @GetMapping("/get/followed-count/{userId}")
    public Long getFollowedCount(@PathVariable String userId) {
        return relationsService.getFollowedCount(userId);
    }

    // フォローリストを取得するエンドポイント
    @GetMapping("/get/follower-list/{userId}")
    public List<String> getFollowerList(@PathVariable String userId) {
        return relationsService.getFollowerList(userId);
    }

    // フォロワーリストを取得するエンドポイント
    @GetMapping("/get/followed-list/{userId}")
    public List<String> getFollowedList(@PathVariable String userId) {
        return relationsService.getFollowedList(userId);
    }

    // // フォローリストからフォロー関係を削除するエンドポイント
    // @DeleteMapping("/delete-follow-list")
    // public ResponseEntity<String> deleteFollowList(
    //         @RequestBody Users followerUser,
    //         @RequestBody Users followedUser
    // ) {
    //     boolean success = relationsService.deleteFollowList(followerUser, followedUser);
    //     if (success) {
    //         return ResponseEntity.ok("フォローリストから削除しました。");
    //     } else {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("フォローリストから削除できませんでした。");
    //     }
    // }
}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class RelationsRequest {
    String userId;
    Users followerId;
    Users followedId;

    public String getUserId(){
        return userId;
    }

    public Users getFollowerId(){
        return followerId;
    }

    public Users getFollowedId(){
        return followedId;
    }
}