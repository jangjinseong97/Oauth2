package com.green.greengram.user.follow;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.follow.model.UserFollowReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/follow")
@Slf4j
@Tag(name = "5. 유저 팔로우")
public class UserFollowController {
    private final UserFollowService userFollowService;

    @PostMapping
    public ResultResponse<Integer> postUserFollow(@RequestBody UserFollowReq p){
        int res = userFollowService.postUserFollow(p);
        return ResultResponse.<Integer>builder().
                resultData(res).
                resultMsg("팔로우").
                build();
    }

    @DeleteMapping
    public ResultResponse<Integer> delUserFollow(@ParameterObject @ModelAttribute UserFollowReq p){
        int res = userFollowService.delUserFollow(p);

        return ResultResponse.<Integer>builder().
                resultData(res).
                resultMsg(res == 1 ? "팔로우 취소 성공":"에러").
                build();
    }
}
