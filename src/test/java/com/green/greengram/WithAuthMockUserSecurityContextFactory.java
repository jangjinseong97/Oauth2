package com.green.greengram;

import com.green.greengram.config.jwt.JwtUser;
import com.green.greengram.config.sercurity.MyUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithAuthMockUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {
    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {
        //에노테이션으로 WithAuthUser 정보가 주입
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        List<String> roles = List.of(annotation.roles()); // [] > List 배열을 리스트로 변환

        JwtUser jwtUser = new JwtUser();
        jwtUser.setSignedUserId(annotation.signedUserId());
        jwtUser.setRoles(roles);

        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setJwtUser(jwtUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
        securityContext.setAuthentication(auth); // 이게 있어야 인증이 되었다고 처리해서
        return securityContext;
        // test 때 인증 처리
    }
}
