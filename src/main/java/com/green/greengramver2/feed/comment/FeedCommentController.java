package com.green.greengramver2.feed.comment;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.comment.model.FeedCommentDelReq;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping
    public ResultResponse<Integer> delFeedComment(@RequestParam("feed_comment_id") long feedCommentId,
                                                  @RequestParam("signed_user_id") long signedUserId){
        FeedCommentDelReq p = new FeedCommentDelReq();
        p.setFeedCommentId(feedCommentId);
        p.setSignedUserId(signedUserId);
        log.info("delFeedComment {}",p.toString());
        int res = service.delFeedComment(p);
        return ResultResponse.<Integer>builder().resultData(res).resultMsg("").build();
    }
}
