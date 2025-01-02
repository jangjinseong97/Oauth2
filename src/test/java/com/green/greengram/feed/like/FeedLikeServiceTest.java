package com.green.greengram.feed.like;

import com.green.greengram.config.sercurity.AuthenticationFacade;
import com.green.greengram.feed.like.model.FeedLikeReq;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FeedLikeServiceTest {

    @InjectMocks
    private FeedLikeService feedLikeService;

    @Mock
    private FeedLikeMapper feedLikeMapper;
    @Mock
    private AuthenticationFacade authenticationFacade;
    private final long SIGNED_USER_ID_3 = 3L;
    private final long FEED_ID_7 = 7L;
    private final long FEED_ID_8 = 8L;

    @BeforeEach
    void setUpAuthenticationFacade() {
        given(authenticationFacade.getSignedUserId()).willReturn(SIGNED_USER_ID_3);
    }
    @Test
    void feedLikeToggleIns() {
        FeedLikeReq givenParam = new FeedLikeReq();
        givenParam.setUserId(SIGNED_USER_ID_3);
        givenParam.setFeedId(FEED_ID_8); //FeedLikeReq 에 3, 8을 넣어서 전송
        given(feedLikeMapper.delFeedLike(givenParam)).willReturn(0);
        given(feedLikeMapper.insFeedLike(givenParam)).willReturn(1);
        // 삭제가 되면 0이 출력 입력이 되면 1이 출력되도록 설정
        FeedLikeReq actualParam = new FeedLikeReq();
        actualParam.setFeedId(FEED_ID_8);
        int actualResult = feedLikeService.feedLikeToggle(actualParam);
        assertEquals(1,actualResult);
    }
    @Test
    void feedLikeToggleDel() {
        FeedLikeReq givenParam = new FeedLikeReq();

        givenParam.setUserId(SIGNED_USER_ID_3);
        givenParam.setFeedId(FEED_ID_7);
        given(feedLikeMapper.delFeedLike(givenParam)).willReturn(1);

        FeedLikeReq actualParam = new FeedLikeReq();
        actualParam.setFeedId(FEED_ID_7);
        int actualResult = feedLikeService.feedLikeToggle(actualParam);

        assertEquals(0, actualResult);
    }
}