package com.green.greengram.user.follow;

import com.green.greengram.config.sercurity.AuthenticationFacade;
import com.green.greengram.user.follow.model.UserFollowReq;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

//Spring TestContext (컨테이너) 이용하는게 아님
@ExtendWith(MockitoExtension.class)
// service 를 테스트 할때만 필요한 것들을 불러옴?
class UserFollowServiceTest {
    @InjectMocks
    UserFollowService userFollowService; // Mockito 가 직접 객체화를 해줌

    @Mock
    // userFollowService 에 넣어줄 가짜 객체를 만들어줌
    UserFollowMapper userFollowMapper;

    @Mock
    AuthenticationFacade authenticationFacade;
    // 이들은 userFollowService 가 저 두개를 맴버필드로 가지므로
    // 거기에 생성자를 통해 값을 넣기 위하여 만들어 주는 것

    static final long FROM_USER_ID1 = 1L;
    static final long TO_USER_ID2 = 2L;
//    static final long FROM_USER_ID3 = 3L;
//    static final long TO_USER_ID4 = 4L;
//
//    static final UserFollowReq USER_FOLLOW_REQ1_2 = new UserFollowReq(TO_USER_ID2);
//    static final UserFollowReq USER_FOLLOW_REQ3_4 = new UserFollowReq(TO_USER_ID4);

    @Test
    @DisplayName("postUserFollow 테스트")
    void postUserFollow() {
        //given
        final int EXPECTED_RESULT = 1;
        // authenticationFacade Mock객체의 getSignedUserId() 메소드를 호출하면 willReturn 값을
        // 리턴시키라고 명령을 내린 것
        given(authenticationFacade.getSignedUserId()).willReturn(FROM_USER_ID1);

        UserFollowReq givenParam1_2 = new UserFollowReq(TO_USER_ID2);
        // givenParam 이 실제 service에서의 p가 되는 것
        givenParam1_2.setFromUserId(FROM_USER_ID1);
        given(userFollowMapper.insUserFollow(givenParam1_2)).willReturn(EXPECTED_RESULT);

        //when
        UserFollowReq actualParam0_2 = new UserFollowReq(TO_USER_ID2);
        int actualResult = userFollowService.postUserFollow(actualParam0_2);

        //then
        assertEquals(EXPECTED_RESULT, actualResult);
    }

    @Test
    @DisplayName("deleteUserFOllow 테스트")
    void deleteUserFollow() {
        //given
        final int EXPECTED_RESULT = 1;
        final long FROM_USER_ID3 = 3L;
        final long TO_USER_ID4 = 4L;
        given(authenticationFacade.getSignedUserId()).willReturn(FROM_USER_ID3);

        UserFollowReq givenParam = new UserFollowReq(TO_USER_ID4);
        givenParam.setFromUserId(FROM_USER_ID3);
        given(userFollowMapper.delUserFollow(givenParam)).willReturn(EXPECTED_RESULT);

        //when
        UserFollowReq actualParam = new UserFollowReq(TO_USER_ID4);
        int actualResult = userFollowService.delUserFollow(actualParam);

        //then
        assertEquals(EXPECTED_RESULT, actualResult);

    }
}