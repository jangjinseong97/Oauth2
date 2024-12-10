package com.green.greengram.user;

import com.green.greengram.common.MyFileUtils;
import com.green.greengram.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils fileUtils;
    public int SignUp(MultipartFile pic, UserSignUpReq p){

        String fileName = pic != null ? fileUtils.makeRandomFileName(pic) : null;
        String hashPwd = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        p.setUpw(hashPwd);
        p.setPic(fileName);
        int res = mapper.signUp(p);
        if(res == 0){
            p.setMsg("중복된 아이디 입니다.");
            return res;
        }

        long userId = p.getUserId();
        String folderPath = String.format("user/%d",userId);
        fileUtils.makeFolder(folderPath);
        // 프로필 사진용 폴더를 처음에 만들지 않아서 후에 프로필사진을 바꿀때 폴더가 없어 에러가 터짐
        // 따라서 회원가입전에 만들어 주도록 변경

        p.setMsg("회원가입 완료");
        if(pic == null){
            return res;
        }

//        String folderPath = String.format("user/%d",userId);
//        fileUtils.makeFolder(folderPath);
        String filePath = String.format("%s/%s",folderPath,fileName);
        try{
            fileUtils.transferTo(pic,filePath);
        } catch(IOException e){
            e.printStackTrace();
        }
        return res;
    }

    public UserSignInRes signIn(UserSignInReq p){
        String uid = p.getUid();
        UserSignInRes res = mapper.selUserByUid(uid);

        if(res == null){
            res = new UserSignInRes();
            res.setMsg("아이디 확인");
            return res;
        }
        log.info(p.toString());
        if(!BCrypt.checkpw(p.getUpw(),res.getUpw())){
            res = new UserSignInRes();
            res.setMsg("비밀번호 확인");
            return res;
        }
        res.setMsg("로그인 성공");
        return res;
    }

    public UserInfoGetRes getUserInfo(UserInfoGetReq p){
        return mapper.selUserInfo(p);
    }

    public String patchUserPic(UserPicPatchReq p){

        String folderPath = String.format("user/%d",p.getSignedUserId());
        fileUtils.makeFolder(folderPath);
        // 혹시 지워졌을때를 대비하여

        // 1. 저장할 파일명 생성
        String newPicName = p.getPic() != null ? fileUtils.makeRandomFileName(p.getPic()) : null ;

        // 2. 기존 파일 삭제

        // 사진을 새로 추가하기전에 기존에 있던 사진을 지워야됨
        // > 폴더를 지우거나 select해서 기존 파일명을 얻어온다(덮어쓰기인듯?)
        // 기존파일명에서 FE를 받는다

        String deletePath = String.format("%s/user/%d",fileUtils.getUploadPath(),p.getSignedUserId());
        fileUtils.deleteFolder(deletePath, false);

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
        if(p.getPic() == null) { return null; }
        // 어쳐피 newPicName으로 인해 pic이 null이면 null 리턴아님?


        return newPicName;
    }


}
