package com.green.greengramver2.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSignUpReq {
    private String uid;
    private String upw;
    private String nickName;

    @JsonIgnore
    private String pic;

    @JsonIgnore
    private long userId;
}
