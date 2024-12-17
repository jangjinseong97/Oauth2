package com.green.greengram.config.jwt;

import com.green.greengram.config.sercurity.MyUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 통합 테스트때 사용
class TokenProviderTest {
    // 테스트는 생성자를 이용한 DI가 불가능
    // DI방법은 필드, setter 메소드, 생성자
    // 테스트할 때는 필드 주입방식을 사용.

    @Autowired // 리플렉션 API를 이용하여 setter가 없이도 주입 가능
    private TokenProvider tokenProvider;

    @Test
    public void generateToken() {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setSignedUserId(10);

        List<String> roles = new ArrayList<>(2);
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        jwtUser.setRoles(roles);

        // when (실행단계)
        String token = tokenProvider.generateToken(jwtUser, Duration.ofHours(3));

        // then (검증단계)
        assertNotNull(token);

        System.out.println("token: " + token);
    }

    @Test
    void validateToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJncmVlbkBncmVlbi5rciIsImlhdCI6MTczNDQwMTQ3MSwiZXhwIjoxNzM0NDAxNTMxLCJzaWduZWRVc2VyIjoie1wic2lnbmVkVXNlcklkXCI6MTAsXCJyb2xlc1wiOltcIlJPTEVfVVNFUlwiLFwiUk9MRV9BRE1JTlwiXX0ifQ.dRn--Y-hPTKPLWp3I9162v6sq4drl8tbPq2gvDieN3ivTid1lEc3zH_cT_Kb4EvpDcM8r6FB2pXwmrmmX3zN4w";
        // 2분짜리

        boolean result = tokenProvider.validToken(token);

        assertFalse(result);

    }

    @Test
    void getAuthentication(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJncmVlbkBncmVlbi5rciIsImlhdCI6MTczNDQwMzAwNSwiZXhwIjoxNzM0NDEzODA1LCJzaWduZWRVc2VyIjoie1wic2lnbmVkVXNlcklkXCI6MTAsXCJyb2xlc1wiOltcIlJPTEVfVVNFUlwiLFwiUk9MRV9BRE1JTlwiXX0ifQ.fK-M0kaf_fDfDC7CeuaOu9RoZ6VOkfTcJpv9fAXJbqfG-h1vxvAAMajdaVGwZ6CwJQ2lsZ_0RC3nRc4tciUxEw";
        // 3시간짜리
        Authentication authentication = tokenProvider.getAuthentication(token);

        assertNotNull(authentication);

        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        JwtUser jwtUser = myUserDetails.getJwtUser();

        JwtUser expectedJwtUser = new JwtUser();
        expectedJwtUser.setSignedUserId(10);

        List<String> roles = new ArrayList<>(2);
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        expectedJwtUser.setRoles(roles);
        assertEquals(expectedJwtUser, jwtUser);
    }
}