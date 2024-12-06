package com.green.greengram.user.follow;

import com.green.greengram.user.follow.model.UserFollowReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFollowMapper {
    int delUserFollow(UserFollowReq p);
    int insUserFollow(UserFollowReq p);
}
