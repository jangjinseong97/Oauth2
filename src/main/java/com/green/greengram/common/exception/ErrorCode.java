package com.green.greengram.common.exception;


public interface ErrorCode {
    String getMessage();
    // enum 엔 없는 것 증 상속받은 enum이 String message 맴버필드를 가지게 하고 싶기 때문
}
