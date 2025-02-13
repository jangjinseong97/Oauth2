package com.green.greengram.config.sercurity.oauth;

import com.green.greengram.common.CookieUtils;
import com.green.greengram.common.GlobalOauth2;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationRequestBasedOnCookieRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final CookieUtils cookieUtils;
    private final GlobalOauth2 globalOauth2;
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        // 쿠키가 null인 상태로 호출 2
        return cookieUtils.getValue(request, globalOauth2.getAuthorizationRequestCookieName(),
                OAuth2AuthorizationRequest.class);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        // OAuth2AuthorizationRequest 로 인한 호출 3
        if(authorizationRequest == null) {
            this.removeAuthorizationRequest(response);
            // 1에서 쿠키가 null이였으니 if문 충족 4
        }
        cookieUtils.setCookie(response, globalOauth2.getAuthorizationRequestCookieName(),
                authorizationRequest, globalOauth2.getCookieExpirySeconds(),
                "/");
        //FE 요청한 redirect_uri 쿠키에 저장
        String redirectUriAfterLogin = request.getParameter(globalOauth2.getRedirectUriParamCookieName());
        cookieUtils.setCookie(response, globalOauth2.getRedirectUriParamCookieName(),
                redirectUriAfterLogin, globalOauth2.getCookieExpirySeconds(),
                "/");
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키가 null이라면 1
        return this.loadAuthorizationRequest(request);
    }

    private void removeAuthorizationRequest(HttpServletResponse response) {
        // 4로 인해 들어온 뒤 삭제
        cookieUtils.deleteCookie(response, globalOauth2.getAuthorizationRequestCookieName());
        cookieUtils.deleteCookie(response, globalOauth2.getRedirectUriParamCookieName());
    }
}
