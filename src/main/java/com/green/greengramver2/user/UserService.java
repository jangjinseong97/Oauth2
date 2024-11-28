package com.green.greengramver2.user;

import com.green.greengramver2.common.MyFileUtils;
import com.green.greengramver2.user.model.UserSignInReq;
import com.green.greengramver2.user.model.UserSignInRes;
import com.green.greengramver2.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        p.setMsg("회원가입 완료");
        if(pic == null){
            return res;
        }
        long userId = p.getUserId();

        String folderPath = String.format("user/%d",userId);
        fileUtils.makeFolder(folderPath);
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
}
