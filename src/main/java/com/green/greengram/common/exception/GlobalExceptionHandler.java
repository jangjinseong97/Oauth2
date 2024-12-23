package com.green.greengram.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice // AOP (Aspect Orientation Programming, 관점 지향 프로그래밍)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 우리가 커스텀한 예외가 발생되었을 경우 캐치
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleException(CustomException e) {
        return handleExceptionInternal(e.getErrorCode());
    }

    // validation 예외가 발생되었을 경우 캐치
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode statusCode,
                                                                  WebRequest request){
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER, ex);
    }
//    @ExceptionHandler(SignatureException.class) //토큰이 오염 되었을 때
//    public ResponseEntity<Object> handleSignatureException() {
//        return handleExceptionInternal(UserErrorCode.UNAUTHENTICATED);
//    }
    // 아래에 합쳐줌

//    // jsonwebtoken 껄 사용해야됨
    @ExceptionHandler({MalformedJwtException.class,java.security.SignatureException.class}) //토큰 값이 유효하지 않을 때
    public ResponseEntity<Object> handleMalformedJwtException() {
        return handleExceptionInternal(UserErrorCode.INVALID_TOKEN);
    }

    @ExceptionHandler(ExpiredJwtException.class) //토큰이 만료가 되었을 때
    public ResponseEntity<Object> handleExpiredJwtException() {
        return handleExceptionInternal(UserErrorCode.EXPIRED_TOKEN);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode){
        return handleExceptionInternal(errorCode, null);
    }

    // 위 아래로 예외가 들어왔을때, 안들어왔을 때

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, BindException e){
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, e));
    }

    private MyErrorResponse makeErrorResponse(ErrorCode errorCode, BindException e){
        return MyErrorResponse.builder()
                .resultMsg(errorCode.getMessage())
                .resultData(errorCode.name())
                .valids(e == null ? null : getValidationError(e))
                .build();
    }

    private List<MyErrorResponse.ValidationErrors> getValidationError(BindException e){
        // enum이라 리스트 내부에 저런식으로 작성

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<MyErrorResponse.ValidationErrors> errors = new ArrayList<>(fieldErrors.size());
        for(FieldError fieldError : fieldErrors){
//            MyErrorResponse.ValidationErrors validationErrors = MyErrorResponse.ValidationErrors.of(fieldError);
            errors.add(MyErrorResponse.ValidationErrors.of(fieldError));
        }

        return errors;
    }


}
