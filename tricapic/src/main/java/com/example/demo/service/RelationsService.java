package com.example.demo.service;

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
    private final RelationsRepo relationsRepository;


    // フォローリクエストを処理するサービスロジック
    @Transactional
    public boolean addFollow(Users followerId, Users followedId) {

            Relations follow = relationsRepository.findByFollowerIdAndFollowedId(followerId, followedId);

        // 登録されたフォローがまだない場合
        if (follow == null) {

            follow = new Relations();
            follow.setFollowerId(followerId);
            follow.setFollowedId(followedId);
            relationsRepository.save(follow);

        } else {
            // 登録済みフォローの場合、失敗
            return false;
        }
        // フォロー登録時の成功
        return true;
    }


    // アンフォローリクエストを処理するサービスロジック
    @Transactional
    public boolean deleteFollow(Users followerUser, Users followedUser) {

        Relations follow = relationsRepository.findByFollowerIdAndFollowedId(followerUser, followedUser);

        // 該当するフォローがあれば
        if (follow != null) {

            // アンフォローする
            relationsRepository.delete(follow);

        } else {

            // アンフォロー失敗
            return false;
        }
        // アンフォロー成功
        return true;
    }

    // ログインユーザーのフォロー数を計算する
    public Long getFollowerCount(String followerUser) {

        return relationsRepository.countByFollowerId(followerUser);
    }

    // ログインユーザーをフォローするユーザー数を計算する
    public Long getFollowedCount(String followedUser) {

        return relationsRepository.countByFollowedId(followedUser);
    }

    // ログインユーザーがフォローするユーザーリスト（フォローリスト）を抽出する
    public List<String> getFollower(String userId) {

        return relationsRepository.findFollowerIdByUserId(userId);
    }

    // ログインユーザーをフォローするユーザーリスト（フォロワーリスト）を抽出する
    public List<String> getFollowed(String userId) {

        return relationsRepository.findFollowedIdByUserId(userId);
    }

    // ログインユーザーのフォローリストからアンフォローリクエストを処理する。
    @Transactional
    public boolean deleteFollowList(Users followerUser, Users followedUser) {

        Relations follow = relationsRepository.findByFollowerIdAndFollowedId(followerUser, followedUser);

        // 該当するフォローがあれば
        if (follow != null) {

            // アンフォローする
            relationsRepository.delete(follow);

        } else {

            // アンフォロー失敗
            return false;
        }
        // アンフォロー成功
        return true;
    }
}