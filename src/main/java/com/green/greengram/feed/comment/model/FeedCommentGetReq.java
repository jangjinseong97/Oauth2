package com.green.greengram.feed.comment.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

@Getter
@Setter
@ToString
public class FeedCommentGetReq {

//    private final static int FIRST_COMMENT_SIZE = 3;
    private final static int DEFAULT_PAGE_SIZE = 20;

    @Positive
    @Schema(name = "feed_id", requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
//    @Schema(title = "페이지", example = "1")
//    private int page;

    @Min(value = 21, message = "사이즈는 20이상이어야 합니다.")
    @Schema(title = "한페이지에 보고 싶은 댓글의 수",description = "안보내면 default 값")
    private int size;
    @PositiveOrZero
    @Schema(name = "start_idx", description = "보이고 있는 댓글 갯수(댓글 element 갯수)", requiredMode = Schema.RequiredMode.REQUIRED)
    private int sIdx;

//    public FeedCommentGetReq(@BindParam("feed_id") long feedId, Integer size,@BindParam("start_idx") int sIdx) {
@ConstructorProperties({"feed_id", "start_idx", "size"})
public FeedCommentGetReq(long feedId, int sIdx, Integer size) {
        this.feedId = feedId;
        this.size = (size == null ? DEFAULT_PAGE_SIZE : size) + 1;
        this.sIdx = sIdx;
    }

//    public FeedCommentGetReq(@BindParam("feed_id") long feedId, int page) {
//        this.feedId = feedId;
//        setPage(page);
//    }
//    public FeedCommentGetReq(){

//    }
//    public void setPage(int page) {
//        this.page = page;
//        if(page < 1) {return;}
//        if(page==1) {
//            sIdx = 0;
//            size = FIRST_COMMENT_SIZE+1;
//            return;
//        }
//        sIdx = ((page-2) *DEFAULT_PAGE_SIZE ) + FIRST_COMMENT_SIZE;
//        size = DEFAULT_PAGE_SIZE +1;
//    }
}