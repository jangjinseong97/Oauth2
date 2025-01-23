package com.green.greengram.feed.comment;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.feed.comment.model.FeedCommentDelReq;
import com.green.greengram.feed.comment.model.FeedCommentGetReq;
import com.green.greengram.feed.comment.model.FeedCommentGetRes;
import com.green.greengram.feed.comment.model.FeedCommentPostReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("feed/comment")
@Tag(name = "3. 댓글 관리")
public class FeedCommentController {
    private final FeedCommentService service;
    @PostMapping
    @Operation(summary = "댓글 작성")
    public ResultResponse<Long> feedCommentPost(@RequestBody FeedCommentPostReq p){
        log.info("feedCommentP {}",p.toString());
        long result = service.insFeedComment(p);
        long res = p.getFeedCommentId();
        return ResultResponse.<Long>builder().
                resultMsg("작성 완료").resultData(res).build();
    }
//    @GetMapping("Parameter")
    @GetMapping
    @Operation(summary = "BindParam")
    public ResultResponse<FeedCommentGetRes> feedCommentGet1(@Valid @ParameterObject @ModelAttribute FeedCommentGetReq p){
        FeedCommentGetRes res = service.getFeedComment(p);
        return ResultResponse.<FeedCommentGetRes>builder().
                resultData(res).
                resultMsg(String.format("%d rows", res.getCommentList().size())).
                build();
    }

    @GetMapping("aa")
    @Operation(summary = "RequestParam")
    public ResultResponse<FeedCommentGetRes> feedCommentGet(@RequestParam("start_idx") int sIdx,
                                                            @RequestParam("feed_id") long feedId,
                                                            @RequestParam(required = false) Integer size){
//        FeedCommentGetReq p = new FeedCommentGetReq();
//        p.setFeedId(feedId);
//        p.setPage(page);
        FeedCommentGetReq p = new FeedCommentGetReq(feedId,size,sIdx);


        log.info("feedCommentGet : {}",p);
        FeedCommentGetRes res = service.getFeedComment(p);

        log.info("feedCommentGet : {}",res);

        return ResultResponse.<FeedCommentGetRes>builder().
                resultData(res).
                resultMsg(String.format("%d rows", res.getCommentList().size())).
                build();
    }

    @DeleteMapping
    @Operation(summary = "삭제")
    public ResultResponse<Integer> delFeedComment(@RequestParam("feed_comment_id") long feedCommentId,
                                                  @RequestParam("signed_user_id") long signedUserId){
        FeedCommentDelReq p = new FeedCommentDelReq(feedCommentId,signedUserId);
        log.info("delFeedComment {}",p);
        int res = service.delFeedComment(p);
        log.info("res {}",res);
        return ResultResponse.<Integer>builder().resultData(res).resultMsg("").build();
    }
    @DeleteMapping("delete")
    public ResultResponse<Integer> delFeedComment2(@ParameterObject @ModelAttribute FeedCommentDelReq p){
        log.info("delFeedComment {}",p);
        int res = service.delFeedComment(p);
        return ResultResponse.<Integer>builder().resultData(res).resultMsg("").build();
    }
}
