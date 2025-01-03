package com.green.greengram.feed.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(title = "피드 등록 응답")
@EqualsAndHashCode
public class FeedPostRes {
    private long feedId;
    private List<String> pics;
}
