package com.green.greengram.feed;

import com.green.greengram.common.MyFileUtils;
import com.green.greengram.common.exception.CustomException;
import com.green.greengram.config.sercurity.AuthenticationFacade;
import com.green.greengram.feed.comment.FeedCommentMapper;
import com.green.greengram.feed.model.FeedPostReq;
import com.green.greengram.feed.model.FeedPostRes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {
    @Mock FeedMapper feedMapper;
    @Mock FeedPicMapper feedPicMapper;
    @Mock FeedCommentMapper feedCommentMapper;
    @Mock MyFileUtils myFileUtils;
    @Mock AuthenticationFacade authenticationFacade;
    @InjectMocks FeedService feedService;

    final long SIGNED_USER_ID = 3L;
    final long FEED_ID_10 = 10L;
    final String LOCATION = "테스트 위치";
    @Test
    @DisplayName("insert 시 영향받은 행이 0개일 때 예외 발생")
    void postFeedInsRows0ThrowException(){
        given(authenticationFacade.getSignedUserId()).willReturn(SIGNED_USER_ID);
        FeedPostReq givenParam = new FeedPostReq();
        givenParam.setWriterUserId(SIGNED_USER_ID);
        givenParam.setLocation(LOCATION);
        given(feedMapper.insFeed(givenParam)).willReturn(0);

        FeedPostReq actualParam = new FeedPostReq();
        actualParam.setLocation(LOCATION);
        assertThrows(CustomException.class
                , () -> feedService.postFeed(null,actualParam));
    }

    @Test
    @DisplayName("MyFileUtils 의 transferTo 호출시 잘못된 경로면 예외 발생")
    void test2() throws Exception{
        given(authenticationFacade.getSignedUserId()).willReturn(SIGNED_USER_ID);
        FeedPostReq givenParam = new FeedPostReq();
        givenParam.setWriterUserId(SIGNED_USER_ID);
        givenParam.setLocation(LOCATION);
        given(feedMapper.insFeed(givenParam)).will(invocation -> {
                FeedPostReq invocationParam = (FeedPostReq) invocation.getArgument(0);
                invocationParam.setFeedId(FEED_ID_10);
            return 1;
        });
        // feedMapper를 실행 했을 때 insFeed의 넣고 싶은값을 세팅
        // invocation 이 앞의 내용을 호출할 당시에 p를 얻어올 수 있음
        // 위의 기준 해당 메소드 호출시 리턴은 1이 리턴되고 p의 FeedId 값이 10으로 세팅됨

        final String SAVED_PIC_NAME_1 = "abc.jpg";
        MultipartFile mpf1 = new MockMultipartFile("pics", "test1.txt", "text/plain", "This is test1 file".getBytes());
        given(myFileUtils.makeRandomFileName(mpf1)).willReturn(SAVED_PIC_NAME_1);

        final String UPLOAD_PATH = "/home/download";
        given(myFileUtils.getUploadPath()).willReturn(UPLOAD_PATH);
        // catch 에서 해당 메소드로 업로드 패치를 받고 있으므로 test때 given으로 제공

        String expectedMiddlePath = String.format("feed/%d", FEED_ID_10);
        String givenFilePath1 = String.format("%s/%s", expectedMiddlePath, SAVED_PIC_NAME_1);

        doAnswer(invocation -> {
            throw new IOException();
        }).when(myFileUtils).transferTo(mpf1,givenFilePath1);
        // doAnswer 는 given과 같은 역할을하지만 void 일때는 given이 사용 불가능 해서 doAnswer를 사용
        // 이때 정상적으로 들어왔을 때 에러를 터트리라고 한 것

        List<MultipartFile> pics = new ArrayList<>(1);
        pics.add(mpf1);

        assertAll(
                () -> {
                    FeedPostReq actualParam = new FeedPostReq();
                    actualParam.setLocation(LOCATION);
                    assertThrows(CustomException.class, () -> feedService.postFeed(pics, actualParam));
                }
                , () -> verify(myFileUtils).makeFolder(expectedMiddlePath)
                // 해당 폴더가 expectedMiddlePath 와 동일하게 만들어졌는지(경로와 이름이 같은지)
                , () -> {
                    String expectedDelFolderPath = String.format("%s/%s", UPLOAD_PATH, expectedMiddlePath);
                    verify(myFileUtils).deleteFolder(expectedDelFolderPath, true);
                    // verify mockito꺼로 해야됨 해당 메소드가 해당루트로 실행됬는지 확인
                }
        );
    }
    @Test
    void test3() {
        given(authenticationFacade.getSignedUserId()).willReturn(SIGNED_USER_ID);

        FeedPostReq givenParam = new FeedPostReq();
        givenParam.setWriterUserId(SIGNED_USER_ID);
        givenParam.setLocation(LOCATION);
        given(feedMapper.insFeed(givenParam)).will(invocation -> {
            FeedPostReq invocationParam = (FeedPostReq) invocation.getArgument(0);
            invocationParam.setFeedId(FEED_ID_10);
            return 1;
        });
        // test의 경우에서 이미 검증했으므로 넘어가기 위해

        FeedPostReq actualParam = new FeedPostReq();
        actualParam.setLocation(LOCATION);

        FeedPostRes actualResult = feedService.postFeed(null, actualParam);
        String picFolderPath = String.format("feed/%d", FEED_ID_10);
        verify(myFileUtils).makeFolder(picFolderPath);
    }
}