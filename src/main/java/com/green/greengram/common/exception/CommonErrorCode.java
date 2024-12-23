package com.green.greengram.common.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR
            ,"서버 내부에서 에러가 발생하였습니다.")
    , INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"잘못된 파라미터 입니다.")
    ;
    // 팔요할때마다 더 작성하면 되는 것

    private final HttpStatus httpStatus;
    private final String message;
    //ErrorCode에 getMessage로 인해 작성해줘야됨 없으면 위의 "~~" 쪽을 메세지에 가져올 수 없음
}
