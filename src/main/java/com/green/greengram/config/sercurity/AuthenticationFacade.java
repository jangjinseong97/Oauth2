package com.green.greengram.config.sercurity;

import com.green.greengram.config.jwt.JwtUser;
import io.jsonwebtoken.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public JwtUser getSignedUser(){
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();

        return myUserDetails.getJwtUser();
    }

    public long getSignedUserId() {
//        JwtUser jwtUser = getSignedUser();
//        return jwtUser.getSignedUserId() == 0 ? 0 : jwtUser.getSignedUserId();
        // 어쳐피 같은 클래스의 메소드니
        return getSignedUser().getSignedUserId();

    }

}
