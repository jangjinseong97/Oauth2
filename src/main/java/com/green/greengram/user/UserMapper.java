package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int signUp(UserSignUpReq p);
    UserSignInRes selUserByUid(String uid);
    UserInfoGetRes selUserInfo(UserInfoGetReq p);
    UserInfoGetRes selUserInfo2(UserInfoGetReq p);
    int patchUserPic(UserPicPatchReq p);
}
