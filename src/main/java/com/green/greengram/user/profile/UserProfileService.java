package com.green.greengram.user.profile;

import com.green.greengram.user.profile.model.UserProfileReq;
import com.green.greengram.user.profile.model.UserProfileRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileMapper userProfileMapper;

    public UserProfileRes selUserProfile(UserProfileReq p){
        UserProfileRes res = userProfileMapper.selUserProfile(p);
        return res;
    }
}
