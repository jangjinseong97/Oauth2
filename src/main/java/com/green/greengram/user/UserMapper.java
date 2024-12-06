package com.green.greengram.user;

import com.green.greengram.user.model.UserSignInRes;
import com.green.greengram.user.model.UserSignUpReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int signUp(UserSignUpReq p);
    UserSignInRes selUserByUid(String uid);
}
