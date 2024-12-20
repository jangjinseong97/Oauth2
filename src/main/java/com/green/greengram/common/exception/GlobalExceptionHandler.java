package com.green.greengram.common.exception;

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
        return null;
    }

    // validation 예외가 발생되었을 경우 캐치
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode statusCode,
                                                                  WebRequest request){
        return null;
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
