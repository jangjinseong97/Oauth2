package com.green.greengram.config.sercurity.oauth.userinfo;

import org.springframework.stereotype.Component;

import java.util.Map;


// 구글 카카오 네이버 플랫폼에서 받은 유저정보 JSON을 HaspMap 형식으로 파싱 HaspMap을 이용한 규격화된 객체로 파싱
@Component
public class Oauth2UserInfoFactory {
    public Oauth2UserInfo getOauth2UserInfo(SignInProviderType signInProviderType, Map<String, Object> attributes) {
        return switch (signInProviderType){
            case KAKAO -> new KakaoOauth2UserInfo(attributes);
            default -> null;
        };
    }
}
