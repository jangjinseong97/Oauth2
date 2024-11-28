package com.green.greengramver2.feed;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.model.FeedPostReq;
import com.green.greengramver2.feed.model.FeedPostRes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("feed")
@Slf4j
public class FeedController {
    private final FeedService feedService;

    @PostMapping
    public ResultResponse<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics,
                                                @RequestPart FeedPostReq p){
        FeedPostRes res =feedService.postFeed(pics, p);
        return ResultResponse.<FeedPostRes>builder().
                resultMessage("피드 등록 완료").
                resultData(res).
                build();
    }
}
