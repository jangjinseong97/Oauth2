package com.green.greengramver2.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengramver2.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
@ToString
public class FeedCommentGetReq {

//    private final static int FIRST_COMMENT_SIZE = 3;
    private final static int DEFAULT_PAGE_SIZE = 20;
    @Schema(name = "feed_id", requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
//    @Schema(title = "페이지", example = "1")
//    private int page;
    @Schema(title = "한페이지에 보고 싶은 댓글의 수",description = "안보내면 default 값")
    private int size;
    @Schema(name = "start_idx", description = "보이고 있는 댓글 갯수(댓글 element 갯수)", requiredMode = Schema.RequiredMode.REQUIRED)
    private int sIdx;

    public FeedCommentGetReq(@BindParam("feed_id") long feedId, Integer size,@BindParam("start_idx") int sIdx) {
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