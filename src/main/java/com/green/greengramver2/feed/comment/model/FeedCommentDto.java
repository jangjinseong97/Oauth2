package com.green.greengramver2.feed.comment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
// data transfer object
public class FeedCommentDto {
    private long writerUserId;
    private long feedCommentId;
    private String comment;
    private String writerNm;
    private String writerPic;
}
