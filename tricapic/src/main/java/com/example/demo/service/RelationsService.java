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
    private final RelationsRepo followRepository;


    // フォローリクエストを処理するサービスロジック
    @Transactional
    public boolean addFollow(Users followerUser, Users followedUser, Long id) {

        Relations follow = followRepository.findByFollowerAndFollowed(followerUser, followedUser);

        // 登録されたフォローがまだない場合
        if (follow == null) {

            follow = new Relations();
            follow.setFollowerId(followerUser);
            follow.setFollowedId(followedUser);
            followRepository.save(follow);

        } else {
            // 登録済みフォローの場合、失敗
            return false;
        }
        // フォロー登録時の成功
        return true;
    }


    // アンフォローリクエストを処理するサービスロジック
    @Transactional
    public boolean deleteFollow(Users followerUser, Users followedUser, Long id) {

        Relations follow = followRepository.findByFollowerAndFollowed(followerUser, followedUser);

        // 該当するフォローがあれば
        if (follow != null) {

            // アンフォローする
            followRepository.delete(follow);

        } else {

            // フォローがない場合はまだ登録されていないというメッセージを表示する
            // アンフォロー失敗
            return false;
        }
        // アンフォロー成功
        return true;
    }

    // ログインユーザーがフォローするユーザー数を計算する
    public Long getFollowerCount(Users followerUser) {

        return followRepository.countByFollower(followerUser);
    }

    // ログインユーザーをフォローするユーザー数を計算する
    public Long getFollowedCount(Users followedUser) {

        return followRepository.countByFollowed(followedUser);
    }

    // ログインユーザーがフォローするユーザーリスト（フォローリスト）を抽出する
    public List<String> getFollower(String userId) {

        return followRepository.findFollowerByUserId(userId);
    }

    // ログインユーザーをフォローするユーザーリスト（フォロワーリスト）を抽出する
    public List<String> getFollowed(String userId) {

        return followRepository.findFollowedByUserId(userId);
    }

    // ログインユーザーのフォローリストからアンフォローリクエストを処理する。
    @Transactional
    public boolean deleteFollowList(Users followerUser, Users followedUser) {

        Relations follow = followRepository.findByFollowerAndFollowed(followerUser, followedUser);

        // 該当するフォローがあれば
        if (follow != null) {

            // アンフォローする
            followRepository.delete(follow);

        } else {

            // フォローがない場合はまだ登録されていないというメッセージを表示する
            return false;
        }
        // アンフォロー成功メッセージ
        return true;
    }
}