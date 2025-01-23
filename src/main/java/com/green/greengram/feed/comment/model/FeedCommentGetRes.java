package com.green.greengram.feed.comment.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FeedCommentGetRes {
    private boolean MoreComment;
    private List<FeedCommentDto> commentList;
}
