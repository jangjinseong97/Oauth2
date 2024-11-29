package com.green.greengramver2.feed.model;

import com.green.greengramver2.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

@Setter
@Getter
@ToString
@Slf4j
public class FeedGetReq extends Paging {
    @Schema(title = "로그인 유저 pk", name = "signed_user_id")
    private long signedUserId;

//    @ConstructorProperties({"page","size","signed_user_id"})
    // 한번에 받고싶은 변수의 이름을 다 정하고 싶을떄
    public FeedGetReq(Integer page, Integer size, @BindParam("signed_user_id") long signedUserId) {
        super(page, size);
        this.signedUserId = signedUserId;
        log.info("FeedGetReq {},{},{}",page,size,signedUserId);
    }
}
