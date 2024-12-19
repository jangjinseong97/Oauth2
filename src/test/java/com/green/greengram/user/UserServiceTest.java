package com.green.greengram.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.config.jwt.JwtProperties;
import com.green.greengram.config.jwt.JwtUser;
import com.green.greengram.config.jwt.TokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Test
    void signIn() {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setSignedUserId(10);
        List<String> aa = new ArrayList<>(2);
        jwtUser.setRoles(aa);

        String accessToken = tokenProvider.generateToken(jwtUser, Duration.ofMinutes(20));
        String refreshToken =tokenProvider.generateToken(jwtUser, Duration.ofDays(15));
        System.out.println("accessToken: " + accessToken);
        System.out.println("refreshToken: " + refreshToken);
    }
}