package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.BindParam;

@Setter
@Getter
@ToString(callSuper = true)
@Slf4j
public class FeedGetReq extends Paging {

//    @Schema(title = "로그인 유저 pk", name = "signed_user_id")
    @JsonIgnore
    private long signedUserId;

    @Positive
    @Schema(title = "프로필 유저 pk" , name ="profile_user_id",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long profileUserId;
    private boolean likeFeed = false;


//    @ConstructorProperties({"page","size","signed_user_id"})
    // 한번에 받고싶은 변수의 이름을 다 정하고 싶을떄
    public FeedGetReq(Integer page, Integer size,
//                      @BindParam("signed_user_id") long signedUserId,
                      @BindParam("profile_user_id") Long profileUserId) {
        super(page, size);
//        this.signedUserId = signedUserId;
        this.profileUserId = profileUserId;
        log.info("FeedGetReq {},{},{}",page,size,signedUserId);
    }

    public void setSignedUserId(long signedUserId) {
        this.signedUserId = signedUserId;
    }
    // 유저아이디
}
