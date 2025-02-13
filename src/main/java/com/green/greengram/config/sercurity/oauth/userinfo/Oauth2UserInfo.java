package com.green.greengram.config.sercurity.oauth.userinfo;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;

/*
플랫폼으로부터 받고 싶은 데이터를 저장하고
원하는 데이터를 리턴하는 규격
 */
@RequiredArgsConstructor
public abstract class Oauth2UserInfo {
    protected final Map<String, Object> attributes; // UserInfo Json > Map

    public abstract String getId(); // 유일값 리턴용도로 쓸 메소드
    public abstract String getName(); // 이름 리턴용도로 쓸 메소드
    public abstract String getEmail(); // 이메일 리턴용도로 쓸 메소드
    public abstract String getProfileImageUrl(); // 프로파일 사진 URL 리턴용도로 쓸 메소드
}
