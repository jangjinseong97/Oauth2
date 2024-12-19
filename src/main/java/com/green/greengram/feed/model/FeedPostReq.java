package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedPostReq {
    @Schema(title = "글 위치", example = "green")
    private String contents;
    @Schema(title = "글 내용")
    private String location;

    @JsonIgnore
//    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private long writerUserId;
    @JsonIgnore
    private long feedId;
}
