package com.green.greengram.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private long feedId;
    // n+1 문제 해결때문에 추가
}
