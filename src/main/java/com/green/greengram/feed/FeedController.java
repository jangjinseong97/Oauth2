package com.green.greengram.feed;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.feed.model.FeedGetReq;
import com.green.greengram.feed.model.FeedGetRes;
import com.green.greengram.feed.model.FeedPostReq;
import com.green.greengram.feed.model.FeedPostRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("feed")
@Slf4j
@Tag(name = "2. 게시글 관리")
public class FeedController {
    private final FeedService feedService;

    @PostMapping
    public ResultResponse<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics,
                                                @RequestPart FeedPostReq p){
        log.info("feedC postFeed p: {}",p.toString());
        FeedPostRes res =feedService.postFeed(pics, p);
        return ResultResponse.<FeedPostRes>builder().
                resultMsg("피드 등록 완료").
                resultData(res).
                build();
    }
    @GetMapping
    @Operation(summary = "feed 리스트", description = "loginUserId는 로그인한 사용자의 pk")
    public ResultResponse<List<FeedGetRes>> getFeedList(@ParameterObject @ModelAttribute FeedGetReq p){
        log.info("feedC getFeedList p: {}",p);
        List<FeedGetRes> list = feedService.getFeedList(p);
        return ResultResponse.<List<FeedGetRes>>builder().
                resultMsg(String.format("%d row",list.size())).resultData(list).build();
    }
}
