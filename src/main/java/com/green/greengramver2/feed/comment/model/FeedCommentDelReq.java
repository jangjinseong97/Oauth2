package com.green.greengramver2.feed.comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
@ToString
public class FeedCommentDelReq {
    private long feedCommentId;
    private long signedUserId;
}
