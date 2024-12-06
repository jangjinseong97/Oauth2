package com.green.greengram.user;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.model.UserSignInReq;
import com.green.greengram.user.model.UserSignInRes;
import com.green.greengram.user.model.UserSignUpReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Tag(name = "1. 사용자 관리")
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    public ResultResponse<Integer> signUp(@RequestPart(required = false) MultipartFile pic,
                                          @RequestPart UserSignUpReq p) {
        int res = service.SignUp(pic, p);

        return ResultResponse.<Integer>builder().
                resultMsg(p.getMsg()).
                resultData(res).
                build();
    }
    @PostMapping("sign-in")
    public ResultResponse<UserSignInRes> signIn(@RequestBody UserSignInReq p) {
        UserSignInRes res = service.signIn(p);
        log.info(res.toString());

        return ResultResponse.<UserSignInRes>builder().
                resultMsg(res.getMsg()).
                resultData(res).
                build();
    }
}
