package com.green.greengram.user.profile;

import com.green.greengram.user.profile.model.UserProfileReq;
import com.green.greengram.user.profile.model.UserProfileRes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserProfileMapper {
    UserProfileRes selUserProfile(UserProfileReq p);
}
