package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data_functions.DataOperation;
import com.example.demo.data_interfaces.UsersRepo;
import com.example.demo.data_tables.Users;
import com.example.demo.service.RelationsService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@RestController
@RequestMapping("/api/relations")
public class RelationsController {

    @Autowired
    private DataOperation dataOperation;
    

    private final RelationsService relationsService;
    private final UsersRepo usersRepo;
    

    public RelationsController(RelationsService relationsService, UsersRepo usersRepo) {
        this.relationsService = relationsService;
        this.usersRepo = usersRepo;
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

    // フォロワー数を取得するエンドポイント
    @GetMapping("/get/followed-count/{userId}")
    public Long getFollowedCount(@PathVariable String userId) {
        return relationsService.getFollowedCount(userId);
    }

    // フレンド数を取得するエンドポイント
    @GetMapping("/get/friends-count/{userId}")
    public int getFriendsCount(@PathVariable String userId) {
        return relationsService.getFriendsCount(userId);
    }

    // フォローリストを取得するエンドポイント(ユーザーIDのみ)
    @GetMapping("/get/follower-list/{userId}")
    public List<String> getFollowerList(@PathVariable String userId) {
        return relationsService.getFollowerList(userId);
    }
    
    // フォロワーリストを取得するエンドポイント(ユーザーIDのみ)
    @GetMapping("/get/followed-list/{userId}")
    public List<String> getFollowedList(@PathVariable String userId) {
        return relationsService.getFollowedList(userId);
    }

    // フレンドリストを取得するエンドポイント(ユーザーID,ユーザー名,アイコン)
    @GetMapping("/get/friends-list/{userId}")
    public List<UsersInfo> getFriendsList(@PathVariable String userId) {
        List<String> friendsIds = relationsService.getFriendsList(userId);
        List<UsersInfo> friendsList = new ArrayList<>();

        for (String friendsId : friendsIds) {
            String friendsName = usersRepo.findNameByUserId(friendsId);
            String friendsIcon = dataOperation.getUserIconByUserId(friendsId);

            UsersInfo result = new UsersInfo();
            result.setUserId(friendsId);
            result.setName(friendsName);
            result.setIconImage(friendsIcon);
            friendsList.add(result);
        }
        return friendsList;
    }

    // フレンドリクエストリストを取得するエンドポイント(ユーザーID,ユーザー名,アイコン)
    @GetMapping("/get/friendsRequest-list/{userId}")
    public List<UsersInfo> getFriendRequestList(@PathVariable String userId) {
        List<String> friendRequestIds = relationsService.getFriendRequestList(userId);
        List<UsersInfo> friendRequestList = new ArrayList<>();

        for (String friendRequestId : friendRequestIds) {
            String friendRequestName = usersRepo.findNameByUserId(friendRequestId);
            String friendsIcon = dataOperation.getUserIconByUserId(friendRequestId);

            UsersInfo result = new UsersInfo();
            result.setUserId(friendRequestId);
            result.setName(friendRequestName);
            result.setIconImage(friendsIcon);
            friendRequestList.add(result);
        }
        return friendRequestList;
    }

    // フレンド関係の状態を取得するエンドポイント (0=関係なし, 1=自分がフォローしている, 2=相手がフォローしている, 3=フレンド)
    @GetMapping("/get/friends-status/{user1Id}/{user2Id}")
    public int getFriendStatus(@PathVariable String user1Id, @PathVariable String user2Id) {
        int status = relationsService.getFriendStatus(user1Id, user2Id);
        return status;
    }
    
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

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class UsersInfo {
    String userId;
    String name;
    String iconImage;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }
}