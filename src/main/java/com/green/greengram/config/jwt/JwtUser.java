package com.green.greengram.config.jwt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class JwtUser {
    private long signedUserId;
    private List<String> roles; // 인가(권한)처리 >> 인증은 로그인만 관련 인가는 권한이 어디까지인지
    //ROLE_이름, ROLE_USER, ROLE_ADMIN
    // 예시인거지 사실 다른걸 담아도 되긴함

}
