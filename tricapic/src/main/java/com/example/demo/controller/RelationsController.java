package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data_tables.Users;
import com.example.demo.service.RelationsService;

@RestController
@RequestMapping("/api/relations")
public class RelationsController {

    private final RelationsService relationsService;

    @Autowired
    public RelationsController(RelationsService relationsService) {
        this.relationsService = relationsService;
    }

    // フォロー関係を追加するエンドポイント
    @PostMapping("/follow")
    public ResponseEntity<String> addFollow(
            @RequestBody Users followerUser,
            @RequestBody Users followedUser,
            @RequestParam Long id
    ) {
        boolean success = relationsService.addFollow(followerUser, followedUser, id);
        if (success) {
            return ResponseEntity.ok("フォローしました。");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("フォローに失敗しました。");
        }
    }

    // フォロー関係を削除するエンドポイント
    @DeleteMapping("/unfollow")
    public ResponseEntity<String> deleteFollow(
            @RequestBody Users followerUser,
            @RequestBody Users followedUser,
            @RequestParam Long id
    ) {
        boolean success = relationsService.deleteFollow(followerUser, followedUser, id);
        if (success) {
            return ResponseEntity.ok("フォローを解除しました。");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("フォロー解除に失敗しました。");
        }
    }

    // 他のエンドポイント：フォロワー数、フォロー数、リストを取得するエンドポイントなど…

    // フォロワー数を取得するエンドポイント
    @GetMapping("/follower-count")
    public ResponseEntity<Long> getFollowerCount(@RequestParam String followerUser) {
        Long count = relationsService.getFollowerCount(followerUser);
        return ResponseEntity.ok(count);
    }

    // フォロー数を取得するエンドポイント
    @GetMapping("/followed-count")
    public ResponseEntity<Long> getFollowedCount(@RequestParam String followedUser) {
        Long count = relationsService.getFollowedCount(followedUser);
        return ResponseEntity.ok(count);
    }

    // フォロワーリストを取得するエンドポイント
    @GetMapping("/follower-list")
    public ResponseEntity<?> getFollowerList(@RequestParam String userId) {
        // 戻り値がStringのリストであると仮定しています。必要に応じて戻り値の型を調整してください。
        return ResponseEntity.ok(relationsService.getFollower(userId));
    }

    // フォローリストを取得するエンドポイント
    @GetMapping("/followed-list")
    public ResponseEntity<?> getFollowedList(@RequestParam String userId) {
        // 戻り値がStringのリストであると仮定しています。必要に応じて戻り値の型を調整してください。
        return ResponseEntity.ok(relationsService.getFollowed(userId));
    }

    // フォローリストからフォロー関係を削除するエンドポイント
    @DeleteMapping("/delete-follow-list")
    public ResponseEntity<String> deleteFollowList(
            @RequestBody Users followerUser,
            @RequestBody Users followedUser
    ) {
        boolean success = relationsService.deleteFollowList(followerUser, followedUser);
        if (success) {
            return ResponseEntity.ok("フォローリストから削除しました。");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("フォローリストから削除できませんでした。");
        }
    }
}

