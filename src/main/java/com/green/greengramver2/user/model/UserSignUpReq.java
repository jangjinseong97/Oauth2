package com.green.greengramver2.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSignUpReq {
    @Schema(title = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(title = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
    @Schema(title = "닉네임")
    private String nickName;

    @JsonIgnore
    private String pic;

    @JsonIgnore
    private long userId;
    @JsonIgnore
    private String msg;
}
