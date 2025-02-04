package com.green.greengram.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FeedCommentPostReq {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String comment;

//    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
//    private long userId;
//    @JsonIgnore
//    private long feedCommentId;
    // JPA로 인해 필요가 없어짐
}
