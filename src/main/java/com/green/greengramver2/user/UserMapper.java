package com.green.greengramver2.user;

import com.green.greengramver2.user.model.UserSignUpReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int signUp(UserSignUpReq p);
}
