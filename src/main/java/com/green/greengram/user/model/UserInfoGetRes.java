package com.green.greengram.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class UserInfoGetRes {
    private int follower;
    private int following;
    private int feedCount;
    private int myFeedLikeCount;
    // 피드 좋아요 숫자? 프로필 사용자의 피드에 달린 좋아요 수?
    // 프로필 사용자의 작성한 글에서 좋아요의 총합
    private int followState;
    private String nickName;
    private String createdAt;
    private String pic;
    private long userId;
}
