package com.green.greengram.common.exception;


import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String getMessage();
    String name();
    // enum 엔 없는 것 증 상속받은 enum이 String message 맴버필드를 가지게 하고 싶기 때문
    HttpStatus getHttpStatus();
    // 응답 코드 결정(에러가 터졌을때 나오는 http 고정)
}
