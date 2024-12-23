package com.green.greengram.user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInReq {
    @Size(min=2, max=30,message = "아이디는 3~30자만 가능")
    @NotNull(message = "아이디 필수로 입력")
    private String uid;
    @Size(min=2, max = 50, message = "비밀번호 3~50자")
    @NotNull(message = "비밀번호 필수로 입력")
    private String upw;
}
