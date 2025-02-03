package com.green.greengram.feed.like;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.BaseIntegrationTest;
import com.green.greengram.WithAuthUser;
import com.green.greengram.feed.like.model.FeedLikeReq;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithAuthUser
//@WithAuthUser(signedUserId = 2L)

public class feedLikeIntegrationTest extends BaseIntegrationTest {
    FeedLikeTestCommon common;
    final String BASE_URL = "/api/feed/like";


    @BeforeAll
    void setUp(){
        common = new FeedLikeTestCommon(objectMapper);
    }

    @Test
    @DisplayName("좋아요 등록")
    void feedLikeReg() throws Exception {
        final long feedIdNotExisted = 5L;
        final int regSuccessResult = 1;
        feedLikeToggle(regSuccessResult,feedIdNotExisted);
    }

    @Test
    @DisplayName("좋아요 취소")
    void feedLikeCancel() throws Exception {
        final long feedIdExisted = 2L;
        final int cancelSuccessResult = 0;
        feedLikeToggle(cancelSuccessResult,feedIdExisted);
    }

    private void feedLikeToggle(final int result, final long feedId) throws Exception {
        ResultActions resultActions = mockMvc.perform(get(BASE_URL).queryParams(common.getParameter(feedId)));

        String expectedResJson = common.getExpectedResJson(result);
        // get방식의 기대 응답의 json을 저장
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson));
    }

}
