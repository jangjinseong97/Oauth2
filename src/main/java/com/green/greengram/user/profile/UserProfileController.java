package com.green.greengram.user.profile;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.user.profile.model.UserProfileReq;
import com.green.greengram.user.profile.model.UserProfileRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("my/user/profile")
@Slf4j
@Tag(name = "99. 프로필")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @GetMapping
    public ResultResponse<UserProfileRes> getUserProfile(@ParameterObject @ModelAttribute
                                                             UserProfileReq p) {
        UserProfileRes res = userProfileService.selUserProfile(p);
        return ResultResponse.<UserProfileRes>builder().
                resultData(res).
                resultMsg("조회 완료").
                build() ;
    }
}
