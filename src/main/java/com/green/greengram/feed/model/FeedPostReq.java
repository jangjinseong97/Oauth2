package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FeedPostReq {
    @Size(max=30, message = "위치는 30자 이하만 가능합니다.")
    @Schema(title = "글 위치", example = "green")
    private String contents;

    @Size(max=1000, message = "내용은 1천자 이하만 가능합니다.")
    @Schema(title = "글 내용")
    private String location;

    @JsonIgnore
//    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private long writerUserId;
    @JsonIgnore
    private long feedId;
}
