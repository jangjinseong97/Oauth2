package com.green.greengram.feed;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jdk.jfr.MetadataDefinition;
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
                                                @Valid @RequestPart FeedPostReq p){
        log.info("feedC postFeed p: {}",p.toString());
        FeedPostRes res =feedService.postFeed(pics, p);
        return ResultResponse.<FeedPostRes>builder().
                resultMsg("피드 등록 완료").
                resultData(res).
                build();
    }
    @GetMapping
    @Operation(summary = "feed 리스트", description = "loginUserId는 로그인한 사용자의 pk")
    public ResultResponse<List<FeedGetRes>> getFeedList(@Valid @ParameterObject @ModelAttribute FeedGetReq p){
        log.info("feedC getFeedList p: {}",p);
        List<FeedGetRes> list = feedService.getFeedList(p);
        return ResultResponse.<List<FeedGetRes>>builder().
                resultMsg(String.format("%d row",list.size())).resultData(list).build();
    }
    @DeleteMapping
    @Operation(summary = "Feed 삭제")
    public ResultResponse<Integer> delFeed(@ParameterObject @ModelAttribute FeedDelReq p){
        int res = feedService.delFeed(p);
        return ResultResponse.<Integer>builder().
                resultMsg("피드 삭제").
                resultData(res).
                build();
    }

    @GetMapping("/3")
    public ResultResponse<List<FeedGetRes>> getFeedList3(@Valid @ParameterObject @ModelAttribute FeedGetReq p){
        log.info("feedC getFeedList p: {}",p);
        List<FeedGetRes> list = feedService.getFeedList3(p);
        return ResultResponse.<List<FeedGetRes>>builder().
                resultMsg(String.format("%d row",list.size())).resultData(list).build();
    }
}
