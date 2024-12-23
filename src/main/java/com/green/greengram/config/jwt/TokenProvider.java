package com.green.greengram.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.exception.CustomException;
import com.green.greengram.common.exception.UserErrorCode;
import com.green.greengram.config.sercurity.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;
    private final SecretKey secretKey;

    public TokenProvider(JwtProperties jwtProperties, ObjectMapper objectMapper) {
        // jwtProperties, objectMapper 둘다 빈등록이 되어 있어서 객체화가 가능하니 두개만 DI 받는 것
        this.jwtProperties = jwtProperties;
        this.objectMapper = objectMapper;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey()));
        // 복호화
    }

    //Jackson 라이브러리 객체주소값을 json으로 바꿔 보내기 위해(객체 주소값으로 보내면 내부의 자료 없이
    // 주소 값만 날아가게 된다) >> 직렬화가 필요

    public String generateToken(JwtUser jwtUser, Duration expiredAt) {
        // duration 만료시간을 위해

        Date now = new Date();
        // 현재시간 (long 값)
        return makeToken(jwtUser, new Date(now.getTime() + expiredAt.toMillis()));
        // new 뒷부분은 만료시간 계산 now로 지금 시간, Duration 타입 expiredAt 로 얼마나 뒤 까지 인지
        // toMillis로 초 단위로 환산 둘다 long 값
    }

    private String makeToken(JwtUser jwtUser, Date expiry){

//        String claimJson;
//        try {
//            claimJson = objectMapper.writeValueAsString(jwtUser);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        // 객체 자체를 JWT에 담고 싶어 객체를 직렬화
        // 즉 jwtUser에 담고 있는 데이터를 Json형태의 문자열로 바꿔줌
        // ++ 메소드를 이용하여 다른곳에 작성
        JwtBuilder builder = Jwts.builder();
        JwtBuilder.BuilderHeader header = builder.header();
        header.type("JWT");

        builder.issuer(jwtProperties.getIssuer());

        return Jwts.builder()
//                .header().add("typ","JWT")
//                .add("alg","HS256")
                // 아래 하나만 줘도 됨
                .header().type("JWT")// JWT 암호화라는걸 알려주는 역할
                .and()
                .issuer(jwtProperties.getIssuer()) //JWT의 발급자(issuer) 정보를 설정
                .issuedAt(new Date()) // 토큰이 발급된 시간(현재시간으로) 설정
                .expiration(expiry) // 토큰 만료 시간(위에서 작성한 expiry에 들어오는 값)
                .claim("signedUser",makeClaim(jwtUser)) // JWT 의 내용 부분에 사용자 정보를 추가
                .signWith(secretKey) // 서명 추가?
                .compact(); // 위의 정보로 JWT 문자열 생성
        // 참고로 데이터량과 무관하게 길이는 동일하게 나옴
    }
    private String makeClaim(JwtUser jwtUser){
        try {
            return objectMapper.writeValueAsString(jwtUser);
            // 직렬화
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    // 객체 자체를 JWT에 담고 싶어 객체를 직렬화
    // 즉 jwtUser에 담고 있는 데이터를 Json형태의 문자열로 바꿔줌

//    public boolean validToken(String token) {
//        // 검증을 위해
//        try{
//            //jwt 복호화
//            getClaims(token);
////            return true;
//        } catch (Exception e) {
//            throw new CustomException(UserErrorCode.EXPIRED_TOKEN);
//        }
//            return true;
//        // 큰차이는 없지만 try안에는 코드가 적을수록 좋아서 빼주는게 좋긴함
//    }

    // spring security 가 인증/인가 할때 사용하는 개체
    // 인증을 위해 객체에 토큰이 있으면 승인/인가 처리 아니면 비승인
    public Authentication getAuthentication(String token) {

        UserDetails userDetails = getUserDetailsFromToken(token);
        return userDetails == null ? null : new UsernamePasswordAuthenticationToken
                (userDetails, null, userDetails.getAuthorities());
        //userDetails 권한
        // 상속관계라 타입이 달라도 리턴 Username~~~ 가 자식

//        Claims claims = getClaims(token);
//        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        // set > collection 종류 객체 주소 값을 담을 수 있음 단 list와 다르게 중복 불가능
        // 뒤의 ROLE_USER 는 권한 이름 ROLE_ 만 붙여주고 뒤에 달리는게 이름
        //return new UsernamePasswordAuthenticationToken();
    }
    public JwtUser getJwtUserFromToken(String token) {
        Claims claims = getClaims(token);
        String json = (String) claims.get("signedUser");
        JwtUser jwtUser = null;
        try {
            jwtUser = objectMapper.readValue(json, JwtUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jwtUser;
    }

    public UserDetails getUserDetailsFromToken(String token) {

//        Claims claims = getClaims(token);
//        String json = (String)claims.get("signedUser");
//        // 객체를 직렬화해서 넣어둔것을 다시 빼온것이므로 다시 객체화가 필요
//        JwtUser jwtUser = null;
//        try {
//            jwtUser = objectMapper.readValue(json, JwtUser.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        // 다시 객체화

        JwtUser jwtUser = getJwtUserFromToken(token);
        MyUserDetails userDetails = new MyUserDetails();
        userDetails.setJwtUser(jwtUser);
        return userDetails;
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}
