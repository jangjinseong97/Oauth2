package com.green.greengram.user;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Tag(name = "1. 사용자 관리")
public class UserController {
    private final UserService service;

    @PostMapping("sign-up")
    @Operation(summary = "유저 회원가입")
    public ResultResponse<Integer> signUp(@RequestPart(required = false) MultipartFile pic,
                                          @RequestPart UserSignUpReq p) {
        int res = service.SignUp(pic, p);

        return ResultResponse.<Integer>builder().
                resultMsg(p.getMsg()).
                resultData(res).
                build();
    }
    @PostMapping("sign-in")
    @Operation(summary = "유저 로그인")
    public ResultResponse<UserSignInRes> signIn(@RequestBody UserSignInReq p,
                                                HttpServletResponse resp) {
        UserSignInRes res = service.signIn(p, resp);
        log.info(res.toString());

        return ResultResponse.<UserSignInRes>builder().
                resultMsg(res.getMsg()).
                resultData(res).
                build();
    }

    @GetMapping
    @Operation(summary = "유저 프로필 정보")
    public ResultResponse<UserInfoGetRes> getUserInfo(@ParameterObject @ModelAttribute UserInfoGetReq p) {
        log.info("userController > getUserInfo > p :{}",p);
        UserInfoGetRes res = service.getUserInfo(p);
        return ResultResponse.<UserInfoGetRes>builder().
                resultData(res).
                resultMsg("유저 프로필 정보").
                build();
    }

    @GetMapping("access-token")
    @Operation(summary = "accessToken 재발행")
    public ResultResponse<String> getAccessToken(HttpServletRequest req) {
        String accessToken = service.getAccessToken(req);
        return ResultResponse.<String>builder()
                .resultMsg("Access Token 재발행")
                .resultData(accessToken)
                .build();
    }

    @PatchMapping("pic")
    @Operation(summary = "사진 수정")
    public ResultResponse<String> patchUserPic(@ModelAttribute UserPicPatchReq p){
        log.info("userController > pathUserPic > p : {}",p);
        String pic = service.patchUserPic(p);
        return ResultResponse.<String>builder().
                resultMsg("프로필 사진 수정 완료").resultData(pic).build();

    }
}
