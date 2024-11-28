package com.green.greengramver2.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 빈등록의 일종 아래 메소드앞에 @Bean이 있으면 그 메소드까지 빈등록을 해줌(리턴 해주는것을)
// (원래는 메소드는 안해줌) 따라서 @Bean 이 들어간다면 해당 메소드는 무조건 리턴값이 있어야됨
@Component // 아래처럼 해주기만 할거였으면 이거로도 충분
public class WebConfiguration implements WebMvcConfigurer {
    private final String uploadPath;

    public WebConfiguration(@Value("${file.directory}")String uploadPath) {
        this.uploadPath=uploadPath;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**").addResourceLocations("file:" + uploadPath + "/");
    } // 사진을 받아오기 위한 설정

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
        // RestController 의 모든 URL에 "/api" prefix 를 설정(앞에 추가로 달림)
        // 이 경우 경로에 api를 작성
        // RestController.class 는 @RestController 에노테이션을 가진 모든 class 에 해당된다는 의미
    }
}
