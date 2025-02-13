package com.green.greengram.config.sercurity.oauth;

import com.green.greengram.config.jwt.JwtUser;
import com.green.greengram.config.sercurity.MyUserDetails;
import com.green.greengram.config.sercurity.oauth.userinfo.Oauth2UserInfo;
import com.green.greengram.config.sercurity.oauth.userinfo.Oauth2UserInfoFactory;
import com.green.greengram.config.sercurity.oauth.userinfo.SignInProviderType;
import com.green.greengram.entity.User;
import com.green.greengram.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import org.springframework.security.core.AuthenticationException;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final Oauth2UserInfoFactory oauth2UserInfoFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req){
        try {
            return process(req);
        }catch (AuthenticationException ex){
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    private OAuth2User process(OAuth2UserRequest req){
        OAuth2User oAuth2User=super.loadUser(req);
        SignInProviderType signInProviderType = SignInProviderType.valueOf(req.getClientRegistration()
                                                            .getRegistrationId()
                // 소셜로그인 신청한 플랫폼 문자열값이 넘어옴 >> yaml에 등록한 값들(spring.security.client.registration 아래의 속성값
                // google, kakao, naver

                                                            .toUpperCase());// 신청한 플렛폼 문자열 값을 대문자로 변경
//        SignInProviderType signInProviderType = SignInProviderType.valueOf("KAKAO");
        Oauth2UserInfo oauth2UserInfo= oauth2UserInfoFactory.getOauth2UserInfo(signInProviderType,oAuth2User.getAttributes());
        // 사용하기 편하게 규격화된 객체로 변환

        //기존에 회원가입을 했는지 체크
        User user = userRepository.findByUidAndProviderType(oauth2UserInfo.getId(), signInProviderType);
        if(user==null){ // 최초 로그인이라면 > 회원가입 처리
            user = new User();
            user.setUid(oauth2UserInfo.getId());
            user.setProviderType(signInProviderType);
            user.setUpw("");
            user.setNickName(oauth2UserInfo.getName());
            user.setPic(oauth2UserInfo.getProfileImageUrl());
            userRepository.save(user);
        }
        JwtUser jwtUser = new JwtUser();
        jwtUser.setSignedUserId(user.getUserId());
        jwtUser.setRoles(new ArrayList<>(1));
        jwtUser.getRoles().add("ROLE_USER");

        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setJwtUser(jwtUser);
        return myUserDetails;
    }
}
