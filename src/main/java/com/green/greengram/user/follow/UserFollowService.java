package com.green.greengram.user.follow;

import com.green.greengram.user.follow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFollowService {
    private final UserFollowMapper userFollowMapper;

    public int postUserFollow(UserFollowReq p){
        int res = userFollowMapper.insUserFollow(p);
        return res;
    }
    public int delUserFollow(UserFollowReq p) {
        int res = userFollowMapper.delUserFollow(p);
        return res;
    }
}
