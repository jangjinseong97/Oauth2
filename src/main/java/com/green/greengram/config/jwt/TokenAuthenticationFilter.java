package com.green.greengram.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@ToString
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        log.info("AuthorizationHeader: {}", authorizationHeader);
        // "Bearer 토큰값" 이 들어가게 됨

        String token = getAccessToken(authorizationHeader);
        log.info("Token: {}", token);
        if(tokenProvider.validToken(token)){
            Authentication auth = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
        // 다음 필터에 request response filterChain 을 넘기는것
    }

    String getAccessToken(String p){
        if(p != null && p.startsWith(TOKEN_PREFIX)){
            // startsWith 는 그걸로 시작하는지 물어보는 것
            return p.substring(TOKEN_PREFIX.length());
            // subString 의 메소드에 1개의 인자(파라미터)를 보내는 것이라
            // 띄어쓰기까지 7자리를 잘라서 보내는 것
        }
        return null;
    }
}
