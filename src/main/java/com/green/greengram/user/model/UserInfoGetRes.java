package com.green.greengram.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class UserInfoGetRes {
    private int follower;
    private int following;
    private int feedCnt;
    private int likeCnt;
    private int followState;
    private String nickName;
    private String createdAt;
    private String pic;
}
