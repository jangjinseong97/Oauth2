package com.green.greengram.common.exception;

import com.green.greengram.common.model.ResultResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@SuperBuilder
// ResultResponse 가 에노테이션으로 builder타입이므로 자식도 가능하도록
// 단 이땐 부모도 SuperBuilder 필요
public class MyErrorResponse extends ResultResponse<String> {
    //validation 에러메시지 전달 역할
    private final List<ValidationErrors> valids;

    // validation 에러가 터지면 해당 에러의 메세지
    // 어떤 필드 였고, 에러 메세지를 묶음으로 담을 객체를 만들 때 사용

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationErrors {
        private String field;
        private String message;

        public static ValidationErrors of(final FieldError fieldError) {
            return ValidationErrors.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
