package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSignInRes {
    private String pic;
    private String nickName;
    @JsonIgnore
    private long userId;
    @JsonIgnore
    private String upw;
    @JsonIgnore
    private String msg;
    private String accessToken;
}
