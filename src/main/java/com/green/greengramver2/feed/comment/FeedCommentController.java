package com.green.greengramver2.feed.comment;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("feed/comment")
public class FeedCommentController {
    private final FeedCommentService service;
    @PostMapping
    public ResultResponse<Long> feedCommentPost(@RequestBody FeedCommentPostReq p){
        log.info("feedCommentP {}",p.toString());
        long result = service.insFeedComment(p);
        long res = p.getFeedCommentId();
        return ResultResponse.<Long>builder().
                resultMsg("작성 완료").resultData(res).build();
    }
}
