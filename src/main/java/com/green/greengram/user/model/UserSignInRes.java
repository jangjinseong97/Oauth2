package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserSignInRes {
    private long userId;
    private String nickName;
    private String pic;
//    @JsonIgnore
    private String accessToken;

}
