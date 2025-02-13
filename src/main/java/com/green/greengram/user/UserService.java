package com.green.greengram.user;

import com.green.greengram.common.CookieUtils;
import com.green.greengram.common.MyFileUtils;
import com.green.greengram.common.exception.CustomException;
import com.green.greengram.common.exception.UserErrorCode;
import com.green.greengram.config.jwt.JwtUser;
import com.green.greengram.config.jwt.TokenProvider;
import com.green.greengram.config.sercurity.oauth.userinfo.SignInProviderType;
import com.green.greengram.entity.User;
import com.green.greengram.user.model.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils fileUtils;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CookieUtils cookieUtils;
    private final UserRepository userRepository;


    public int SignUp(MultipartFile pic, UserSignUpReq p){

        String fileName = pic != null ? fileUtils.makeRandomFileName(pic) : null;
//        String hashPwd = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        String hashPwd = passwordEncoder.encode(p.getUpw());

//        p.setUpw(hashPwd);
//        p.setPic(fileName);

        User user = new User();
        user.setProviderType(SignInProviderType.LOCAL);
        user.setNickName(p.getNickName());
        user.setUid(p.getUid());
        user.setUpw(hashPwd);
        user.setPic(fileName);

//        int res = mapper.signUp(p);
        userRepository.save(user);

        if(pic == null){
            return 1;
        }

//        if(res == 0){
//            p.setMsg("중복된 아이디 입니다.");
//            return res;
//        }
//        p.setMsg("회원가입 완료");
//        if(pic == null){
//            return res;
//        }

        long userId = user.getUserId();
        String folderPath = String.format("user/%d",userId);
        fileUtils.makeFolder(folderPath);
        // 프로필 사진용 폴더를 처음에 만들지 않아서 후에 프로필사진을 바꿀때 폴더가 없어 에러가 터짐
        // 따라서 회원가입전에 만들어 주도록 변경


//        String folderPath = String.format("user/%d",userId);
//        fileUtils.makeFolder(folderPath);
        String filePath = String.format("%s/%s",folderPath,fileName);
        try{
            fileUtils.transferTo(pic,filePath);
        } catch(IOException e){
            e.printStackTrace();
        }
        return 1;
    }

    public UserSignInRes signIn(UserSignInReq p , HttpServletResponse response){

//        User user = userRepository.findByUid(p.getUid());
        User user = userRepository.findByUidAndProviderType(p.getUid(), SignInProviderType.LOCAL);
        String uid = p.getUid();
//        UserSignInRes res = mapper.selUserByUid(uid);

        if(user == null || !passwordEncoder.matches(p.getUpw(), user.getUpw())){
            throw new CustomException(UserErrorCode.INCORRECT_ID_PW);
        }

        // 위의 코드가 아래의 코드들 역할
//        if(res == null){
//            res = new UserSignInRes();
//            res.setMsg("아이디 확인");
//            return res;
//        }
//        log.info(p.toString());
////        if(!BCrypt.checkpw(p.getUpw(),res.getUpw())){
//        if(!passwordEncoder.matches(p.getUpw(),res.getUpw())){
//            res = new UserSignInRes();
//            res.setMsg("비밀번호 확인");
//            return res;
//        }

        JwtUser jwtUser = new JwtUser();
        jwtUser.setSignedUserId(user.getUserId());
        jwtUser.setRoles(new ArrayList<>(2));
        jwtUser.getRoles().add("ROLE_USER");
        jwtUser.getRoles().add("ADMIN");
//        String accessToken = tokenProvider.generateToken(jwtUser, Duration.ofMinutes(20));
        String accessToken = tokenProvider.generateToken(jwtUser, Duration.ofMinutes(30));
        String refreshToken =tokenProvider.generateToken(jwtUser, Duration.ofDays(15));

//        res.setAccessToken(accessToken);
        // refreshToken 은 쿠키에 담음
        int maxAge = 1_296_000; // 15*24*60*60 15일의 초 값
        cookieUtils.setCookie(response,"refreshToken",refreshToken,maxAge, "/api/user/access-token");
//        res.setMsg("로그인 성공");
//        log.info("res: {}",res);
        return new UserSignInRes(user.getUserId(),
                user.getNickName(),
                user.getPic(),
                 accessToken);
    }

    public UserInfoGetRes getUserInfo(UserInfoGetReq p){
        return mapper.selUserInfo(p);
    }

    public String getAccessToken(HttpServletRequest req){
        Cookie cookie = cookieUtils.getValue(req,"refreshToken");
        String refreshToken = cookie.getValue();
        log.info("refreshToken : {}",refreshToken);
//        UserDetails a = tokenProvider.getUserDetailsFromToken(refreshToken);
        JwtUser jwtUser = tokenProvider.getJwtUserFromToken(refreshToken);
        String accessToken = tokenProvider.generateToken(jwtUser, Duration.ofMinutes(20));

        return accessToken;
    }

    public String patchUserPic(UserPicPatchReq p){


        // 1. 저장할 파일명 생성
        String newPicName = p.getPic() != null ? fileUtils.makeRandomFileName(p.getPic()) : null ;

        String folderPath = String.format("user/%d",p.getSignedUserId());
        fileUtils.makeFolder(folderPath);
        // 혹시 지워졌을때를 대비하여 파일생성

        // 2. 기존 파일 삭제

        // 사진을 새로 추가하기전에 기존에 있던 사진을 지워야됨
        // > 폴더를 지우거나 select해서 기존 파일명을 얻어온다(덮어쓰기인듯?)
        // 기존파일명에서 FE를 받는다

        String deletePath = String.format("%s/user/%d",fileUtils.getUploadPath(),p.getSignedUserId());
        fileUtils.deleteFolder(deletePath, false);



        log.info("서비스 p 값 : {}",p);
//        if(p.getPic() == null) { return null; }
        if(p.getPic() == null) { return newPicName; }
        // 어쳐피 newPicName으로 인해 pic이 null이면 null 리턴아님?
        // 아래의 코드 실행시 null값이 들어간 상황에서 문제가 생겨 여기서 null을 반환하는 것

        //3. 원하는 위치에 저장할 파일명으로 파일을 저장

//        String folderPath = String.format("user/%d",p.getSignedUserId());
//        fileUtils.makeFolder(folderPath);
//        String filePath = String.format("%s/%s",folderPath,newPicName);
        String filePath = String.format("user/%d/%s",p.getSignedUserId(),newPicName);
        try {
            fileUtils.transferTo(p.getPic(),filePath);

        }catch (IOException e){
            e.printStackTrace();
        }

        // 4. db의 튜플을 수정으로 바꿈
        p.setPicName(newPicName);
        int a = mapper.patchUserPic(p);


        return newPicName;
    }


}
