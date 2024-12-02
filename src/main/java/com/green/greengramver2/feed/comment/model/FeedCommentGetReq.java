package com.green.greengramver2.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengramver2.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCommentGetReq {

    private final static int FIRST_COMMENT_SIZE = 3;
    private final static int DEFAULT_PAGE_SIZE = 20;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
    private int page;
    @JsonIgnore
    private int size;
    @JsonIgnore
    private int sIdx;

    public void setPage(int page) {
        if(page < 1) {return;}
        if(page==1) {
            sIdx = 0;
            size = FIRST_COMMENT_SIZE+1;
            return;
        }
        sIdx = ((page -1) *DEFAULT_PAGE_SIZE +3) + FIRST_COMMENT_SIZE;
        size = DEFAULT_PAGE_SIZE +1;
    }
}