package com.green.greengram.config.sercurity;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration //메소드 빈등록이 었어야 유의미한 에노테이션 + 싱글톤
// 만약 @Component 이였다면 아래의 @Bean 이 필요 없지만 싱글톤이 되지 않아
// 호출마다 스프링컨테이너에 새로 객체를 생성
@RequiredArgsConstructor
public class WebSecurityConfig {
//    // 스프링 시큐리트 기능 비활성화(관여하지 않았으면 하는 부분)
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//                .requestMatchers(new AntPathRequestMatcher("/static/**"));
//        // security 가 위의 static경로로 오는건 비활성화(관여하지않음)
//    }
    @Bean
    // 스프링이 메소드 호출을 하고 리턴한 객체의 주소값을 관리 (빈등록)
    // 즉 리턴한게 빈등록 되어 있는것

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 시큐리티가 세션을 사용하지 않는걸 의미

                // 쳐피 화면을 만들지 않아서 SSR이 아니므로 필요 없는부분을 꺼서 리소스확보를 하는 것
                .httpBasic(h -> h.disable())
                //SSR(Server Side Rendering)이 아니다.
                //화면을 만들지 않을거라 비활성화 시킨것 >> 시큐리티 로그인창이 나타나지 않게됨
                .formLogin(from -> from.disable())
                //폼로그인 기능 자체를 비활성화(SSR이 아니라서)
                .csrf(csrf -> csrf.disable())
                // 보안관련 SSR 이 아니면 보안이슈가 없기에 기능을 끄는 것
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/api/feed","/api/feed/3").authenticated()
                                // 로그인이 되어 있어야 허용
                            .anyRequest().permitAll())
                            // 나머지는 다 허용 >> 무조건 아래 있어야 함 위에 있으면 전부 허용이 되어버림

                .build();
    }

}
