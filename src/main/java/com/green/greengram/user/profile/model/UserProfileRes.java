package com.green.greengram.user.profile.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileRes {
    private int followCnt;
    private int followerCnt;
    private int feedCnt;
    private int likeCnt;
    private String nickName;
    private String createdAt;
    private String pic;
    // signed_user_id 와 프로필 사용자의 팔로우 상태
    // 팔로우 수
    // 팔로워 수
    // 게시글 수
    // 유저 사진
    // 좋아요한 게시글 수


}
