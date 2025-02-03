package com.green.greengram.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FeedCommentDelReq {
    @Schema(name = "feed_comment_id")
    private long feedCommentId;
//    @Schema(name = "signed_user_id")
    @JsonIgnore
    private long signedUserId;

//    @ConstructorProperties({"feed_comment_id, signed_user_id"})
    @ConstructorProperties({"feed_comment_id"})
//    public FeedCommentDelReq(long feedCommentId, long signedUserId) {
    public FeedCommentDelReq(long feedCommentId) {
        this.feedCommentId = feedCommentId;
//        this.signedUserId = signedUserId;
    }
    public FeedCommentDelReq() {}
    // modelAttribute 로 생성자를 이용해 값을 넣어주면 기본생성자부터 이용하게 되어 있으면 안됨(위 처럼 통일하는게 불가능)
}
