package com.green.greengram.config.sercurity.oauth;

import com.green.greengram.common.GlobalOauth2;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2AuthenticationCheckRedirectUriFilter extends OncePerRequestFilter {
    // 추상클래스라서x 추상메소드를 가지고있어서o 추상클래스라고 무조건 추상메소드를 가지는건 아니기 떄문

    private final GlobalOauth2 globalOauth2;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
           호스트 주소값을 제외한 요청한 uri
           ex) http://loaclhost:8080/aaa/bbb 지금 기준 http://localhost:8080/oauth2/authorzation
           호스트 주소값: http://loaclhost:8080
           제외한 요청한 주소값: /aaa/bbb
         */
        String requestUri = request.getRequestURI();
        log.info("request uri: {}", requestUri);
        if(requestUri.startsWith(globalOauth2.getBaseUri())){
            //소셜 로그인 요청이라면
            String redirectUri = request.getParameter("redirect_uri");
            if(redirectUri != null && !hasAuthorizedRedirectUri(redirectUri)){
                // 약속한 redirect_uri 값이 아니였다면
                String errRedirecturl = UriComponentsBuilder.fromUriString("/err_message")
                        .queryParam("message","유효한 Redirect URL이 아닙니다.").encode().toUriString();
                //위를 사용하여 errRedirectUrl = "/err_message?message=유효한 Redirect URL이 아닙니다." 가 만들어짐
                response.sendRedirect(errRedirecturl);
                return;
            }
        }
        filterChain.doFilter(request, response); // 필터는 항상 다음필터에 넘기는 이 부분이 필요
    }
    //약속한 redirect_uri 가맞는지 체크 있으면 true 없으면 false
    private boolean hasAuthorizedRedirectUri(String redirectUri) {
        for(String uri: globalOauth2.getAuthorizedRedirectUris()) {
            if(uri.equals(redirectUri)) {
                return true;
            }
        }

        return false;
    }
}
