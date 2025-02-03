package com.green.greengram.feed.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.feed.comment.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FeedCommentController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
// 컨트롤러만 테스트 한다고 준 것이라 @MockBean으로 가짜를 만들어 넣는것

// @Import({MyFileUtils.class}) 만약 진짜를 넣고 싶으면 >> 빈등록도 해줌
class FeedCommentControllerTest {

    @Autowired ObjectMapper objectMapper; //json 사용
    @Autowired MockMvc mockMvc; // 요청(보내고) + 응답(받기) 처리
    @MockBean FeedCommentService feedCommentService; // >> MockBean 이 빈등록 해줌
    // 원본 controller에서 FeedCommentService 객체가 있고 이를 생성자로 받고 있어서

//    private final FeedCommentService feedCommentServiceMock; >> 이건 왜 안써지는거임? >> 빈등록 안되니까

    // 위에서 가짜를 만드는 이유와 비슷하게 객체도 가짜로 만들어서 사용하는것
    final String URL = "/api/feed/comment";
    final long feedId_2 =2L;
    final long feedCommentId_3 =3L;
    final long writerUserId_4 =4L;
    final int SIZE = 20;

    @Test
    void feedCommentGet1() throws Exception {


        FeedCommentGetReq givenParam = new FeedCommentGetReq(feedId_2,1,SIZE);

        FeedCommentDto feedCommentDto = new FeedCommentDto();
        feedCommentDto.setFeedId(feedId_2);
        feedCommentDto.setFeedCommentId(feedCommentId_3);
        feedCommentDto.setComment("댓");
        feedCommentDto.setWriterUserId(writerUserId_4);
        feedCommentDto.setWriterNm("작성자");
        feedCommentDto.setWriterPic("profile.jpg");

        FeedCommentGetRes expectedResult = new FeedCommentGetRes();
        expectedResult.setMoreComment(false);
//        expectedResult.setCommentList(new ArrayList<>(2));
//        아래처럼 한번에 하면 편함 아니면 생성후 직접 주입
        expectedResult.setCommentList(List.of(feedCommentDto));
        String getUrl = "/api/feed/comment/Parameter";

        given(feedCommentService.getFeedComment(givenParam)).willReturn(expectedResult);
        System.out.println("res" + expectedResult);
        ResultActions resultActions = mockMvc.perform(get(URL).queryParams(getParams()));

        String expectedResJson = getExpectedResJson(expectedResult);
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson));
        verify(feedCommentService).getFeedComment(givenParam);
    }

    private MultiValueMap<String, String> getParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("feed_id", String.valueOf(feedId_2));
        params.add("start_idx", "1");
        params.add("size", String.valueOf(SIZE));
        return params;
    }

    private String getExpectedResJson(FeedCommentGetRes res)throws Exception{
        ResultResponse response = ResultResponse.<FeedCommentGetRes>builder()
                .resultMsg(String.format("%d rows", res.getCommentList().size()))
                .resultData(res)
                .build();
        return objectMapper.writeValueAsString(response);
    }

    @Test
    @DisplayName("피드 댓글 등록")
    void postFeedComment() throws Exception {
        FeedCommentPostReq givenParam = new FeedCommentPostReq();
        givenParam.setFeedId(feedId_2);
        givenParam.setComment("댓글");

        given(feedCommentService.insFeedComment(givenParam)).willReturn(feedCommentId_3);
        //
        ResultResponse res = ResultResponse.<Long>builder()
                .resultData(feedCommentId_3)
                .resultMsg("작성 완료")
                .build();
        String expectedResJson = objectMapper.writeValueAsString(res);
        // 어떤 값을 받고싶은지에 대해서라 위에서 지정한 리턴값과 같은 데이터를 받을 수 있어야 된다.

        String givenParamJson = objectMapper.writeValueAsString(givenParam);

        ResultActions resultActions =mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(givenParamJson));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson));

        verify(feedCommentService).insFeedComment(givenParam);
    }

    @Test
    @DisplayName("피드 댓글 삭제")
    void delFeedComment() throws Exception {
        final int RESULT = 3;
        FeedCommentDelReq givenParam = new FeedCommentDelReq(feedCommentId_3);
        given(feedCommentService.delFeedComment(givenParam)).willReturn(RESULT);
        ResultActions resultActions= mockMvc.perform(delete(URL).queryParam("feedCommentId",String.valueOf(feedCommentId_3)));

        String expectedResJson = objectMapper.writeValueAsString(ResultResponse.<Integer>builder()
                        .resultMsg("")
                        .resultData(RESULT)
                        .build());
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson));
        verify(feedCommentService).delFeedComment(givenParam);
    }


}