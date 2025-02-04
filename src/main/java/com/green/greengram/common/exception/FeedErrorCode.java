package com.green.greengram.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FeedErrorCode implements ErrorCode {
    FAIL_TO_REG(HttpStatus.INTERNAL_SERVER_ERROR, "피드 등록 이슈"),
    FAIL_TO_DELETE(HttpStatus.BAD_REQUEST, " 피드 삭제 실패"),
    FAIL_TO_DEL_COMMENT(HttpStatus.BAD_REQUEST, "댓글 삭제 실패")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
