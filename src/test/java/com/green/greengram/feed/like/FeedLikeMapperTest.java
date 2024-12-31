package com.green.greengram.feed.like;

import com.green.greengram.TestUtils;
import com.green.greengram.feed.like.model.FeedLikeReq;
import com.green.greengram.feed.like.model.FeedLikeVo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test") // yaml 적용되는 파일 선택 (application-test.yaml) 에서 뒤의 test
@MybatisTest // Mybatis Mapper Test 이므로 작성 >> Mapper 들이 전부 객체화
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// 테스트는 기본적으로 메모리 데이터베이스(H2)를 사용해서 하는데 기본적으로 사용되는
// 메모리 데이터베이스로 교체하지 않고 우리가 원래 쓰던 데이터베이스로 테스트를 하겠다는 의미
// replace 부분을 none로 줬으므로



//@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // static 뺼수 있음(클래스당 1개의 객체)
//@TestInstance(TestInstance.Lifecycle.PER_METHOD) // static 뺼수 없음(클래스당 여러개의 객체 가능) 디폴트

class FeedLikeMapperTest {

    @Autowired // DI용 에노테이션
    FeedLikeMapper feedLikeMapper; // DI가 된다.
    // 필드에서 주입해주는 방식이라 private 달아도 가능

    @Autowired
    FeedLikeTestMapper feedLikeTestMapper;
    static final long FEED_ID_1 = 1L;
    static final long USER_ID_2 = 2L;
    static final long FEED_ID_5 = 5L;
    static final FeedLikeReq existedData = new FeedLikeReq();
    static final FeedLikeReq notExistedData = new FeedLikeReq();
    // @BeforeAll - 테스트 메소드 실행 되기 최초 딱 한번 실행이 되는 메소드
    // 테스트 메소드 마다 테스트 객체가 만들어지면 BeforeAll 메소드는 static 메소드여야 한다.
    // 한 테스트 객체가 만들어지면 non-static 메소드일 수도 있다.
    /*
        @BeforeAll - 모든 테스트 실행 전에 최초 한번 실행
        --
        @BeforeEach - 각 테스트 실행 전에 실행
        @Test
        @AfterEach - 각 테스트 실행 후에 실행
        --
        @AfterAll - 모든 테스트 실행 후에 최초 한번 실행

     */


    @BeforeAll
    static void initData(){
        existedData.setFeedId(FEED_ID_1);
        existedData.setUserId(USER_ID_2);

        notExistedData.setFeedId(FEED_ID_5);
        notExistedData.setUserId(USER_ID_2);
    }

    // @BeforeEach - 테스트 메소드 마다 테스트 메소드 실행 전에 실행되는 메소드(각각 실행 됨)
    // 어떤 test 메소드를 실행하기 전에 무조건 실행 되는것


    @Test
    void insFeedLikeDuplicatedDataThrowDuplicateKeyException() {
        //given (준비)
        // L은 long타입을 명시 해주는 것
//        FeedLikeReq feedLikeReq = new FeedLikeReq();
//        feedLikeReq.setFeedId(FEED_ID_1);
//        feedLikeReq.setUserId(USER_ID_2);

    // 위의 existedData 로 필요가 없어짐

        //when (실행)
//        int actualAffectedRows = feedLikeMapper.insFeedLike(feedLikeReq);

        //then (단언, 체크)
//        assertEquals(1, actualAffectedRows);
        // 영향받은 튜플이 있으면 1이 넘어올거니 결과값이 1과 같은지 체크
        //when (실행)
        //then (단언, 체크)
        assertThrows(DuplicateKeyException.class, () -> {
            feedLikeMapper.insFeedLike(existedData);
        }, "데이터 중복시 에러 발생되지 않음 > Unique(feed_id, user_id) 확인 바람");
        // 에러를 터지길 바랬던 것이라 에러가 터져야 test result가 성공으로 나옴
    }
    @Test
    void insFeedLikeNormal(){
        int actualAffectedRows = feedLikeMapper.insFeedLike(notExistedData);
        assertEquals(1, actualAffectedRows);
    }

    @Test
    void delFeedLikeNoData(){
        int actualAffectedRows = feedLikeMapper.delFeedLike(notExistedData);
        assertEquals(0, actualAffectedRows);
    }

    @Test
    void delFeedLike(){
        FeedLikeVo actualFeedLikeVoBefore = feedLikeTestMapper.selFeedLikeByFeedIdAndUserId(existedData);
        int actualAffectedRows = feedLikeMapper.delFeedLike(existedData);
        FeedLikeVo actualFeedLikeVoAfter = feedLikeTestMapper.selFeedLikeByFeedIdAndUserId(existedData);

        assertAll(
                () -> assertNotNull(actualFeedLikeVoBefore) // 값이 있었음
                , () -> assertNull(actualFeedLikeVoAfter) // 호출된 후 값이 사라짐 즉 내가 원하는 값이 삭제됨
                , () -> assertEquals(1, actualAffectedRows)
        );

    }

    @Test
    void insFeedLike(){

        //when
        List<FeedLikeVo> actualFeedLikeListBefore = feedLikeTestMapper.selFeedLikeAll(); // insert 전 튜플 수
        FeedLikeVo actualFeedLikeVoBefore = feedLikeTestMapper.selFeedLikeByFeedIdAndUserId(notExistedData);
        //inesrt 하려는 데이터가 존재하는지 (null을 원한것)
        int actualAffectedRows = feedLikeMapper.insFeedLike(notExistedData);
        FeedLikeVo actualFeedLikeVoAfter = feedLikeTestMapper.selFeedLikeByFeedIdAndUserId(notExistedData);
        // 위에서 insert 한 데이터가 존재하는지 (값이 있길 원한 것)
        List<FeedLikeVo> actualFeedLikeListAfter = feedLikeTestMapper.selFeedLikeAll(); // insert 후 튜플 수


        // 위 작업들은 넣기 전과 후를 비교하기 위하여

        //then
        assertAll(
                () -> TestUtils.assertCurrenTimeStamp(actualFeedLikeVoAfter.getCreatedAt())
                , () -> assertEquals(actualFeedLikeListBefore.size()+1,actualFeedLikeListAfter.size())
                , () -> assertNull(actualFeedLikeVoBefore)
                , () -> assertNotNull(actualFeedLikeVoAfter)
                , () -> assertEquals(1, actualAffectedRows)
                , () -> assertEquals(notExistedData.getFeedId() , actualFeedLikeVoAfter.getFeedId())
                , () -> assertEquals(notExistedData.getUserId() , actualFeedLikeVoAfter.getUserId())
        );
    }
}