package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.data_interfaces.RelationsRepo;
import com.example.demo.data_tables.Relations;
import com.example.demo.data_tables.Users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RelationsService {
    private final RelationsRepo relationsRepo;


    // フォローリクエストを処理するサービスロジック
    @Transactional
    public boolean addFollow(Users followerId, Users followedId) {

            Relations follow = relationsRepo.findByFollowerIdAndFollowedId(followerId, followedId);

        // 登録されたフォローがまだない場合
        if (follow == null) {

            follow = new Relations();
            follow.setFollowerId(followerId);
            follow.setFollowedId(followedId);
            relationsRepo.save(follow);

        } else {
            // 登録済みフォローの場合、失敗
            return false;
        }
        // フォロー登録時の成功
        return true;
    }


    // アンフォローリクエストを処理するサービスロジック
    @Transactional
    public boolean deleteFollow(Users followerId, Users followedId) {

        Relations follow = relationsRepo.findByFollowerIdAndFollowedId(followerId, followedId);

        // 該当するフォローがあれば
        if (follow != null) {

            relationsRepo.delete(follow);

        } else {

            // アンフォロー失敗
            return false;
        }
        // アンフォロー成功
        return true;
    }

    // ユーザーのフォロー数を計算する
    public Long getFollowerCount(String followerUser) {
        return relationsRepo.countByFollowerId(followerUser);
    }

    // ユーザーのフォロー数を計算する
    public Long getFollowedCount(String followedUser) {
        return relationsRepo.countByFollowedId(followedUser);
    }

    // ユーザーのフォローリストを参照する
    public List<String> getFollowerList(String userId) {
    return relationsRepo.findFollowerIdByUserId(userId);
}

    // ユーザーのフォロワーリストを参照する
    public List<String> getFollowedList(String userId) {
        return relationsRepo.findFollowedIdByUserId(userId);
    }

    //フレンド数の取得
    public int getFriendsCount(String userId) {
        List<String> friendsList = getFriendsList(userId);
        return friendsList.size();
    }

    //ユーザーのフレンドリストの参照(相互フォロー)
    public List<String> getFriendsList(String userId) {
        List<String> followerList = relationsRepo.findFollowerIdByUserId(userId);
        List<String> followedList = relationsRepo.findFollowedIdByUserId(userId);
        
        List<String> friendsList = new ArrayList<>(followerList);
        friendsList.retainAll(followedList);
        
        return friendsList;
}

    //ユーザーのフレンドリクエストリスト
    public List<String> getFriendRequestList(String userId) {
        List<String> followedList = getFollowedList(userId);
        List<String> friendsList = getFriendsList(userId);

        List<String> friendRequestList = new ArrayList<>(followedList);
        friendRequestList.removeAll(friendsList);
        
        return friendRequestList;
}

    //フレンド関係の判定
    public int getFriendStatus(String user1Id, String user2Id) {
        Relations relation1 = relationsRepo.findByFollowerIdAndFollowedId(user1Id, user2Id);
        Relations relation2 = relationsRepo.findByFollowerIdAndFollowedId(user2Id, user1Id);
        
        int status = 0;

    if (relation1 != null && relation2 != null) {
        // フレンドの時
        status = 3;
    } else if (relation1 != null) {
        // user1がuser2をフォローしている時
        status = 1;
    } else if (relation2 != null) {
        // user2がuser1をフォローしている時
        status = 2;
    }

    return status;
}
}