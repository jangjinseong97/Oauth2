package com.green.greengram.feed;

import com.green.greengram.TestUtils;
import com.green.greengram.feed.model.FeedPicDto;
import com.green.greengram.feed.model.FeedPicVo;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedPicMapperTest {
    @Autowired
        FeedPicMapper feedPicMapper;
    @Autowired
        FeedPicTestMapper feedPicTestMapper;

    @Test
        void insFeedPicNoFeedIdThrowsForeignKeyException() {
            FeedPicDto givenParam = new FeedPicDto();
            givenParam.setFeedId(10L);
            givenParam.setPics(new ArrayList<>(1));
            givenParam.getPics().add("a.jpg");
            assertThrows(DataIntegrityViolationException.class, () -> feedPicMapper.insFeedPic(givenParam));
    }

    @Test
    void insFeedPicNoPicThrowNotNullException() {
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(1L);
        givenParam.setPics(new ArrayList<>());
        assertThrows(BadSqlGrammarException.class, () -> feedPicMapper.insFeedPic(givenParam));
    }

    @Test
    void insFeedPic(){

        String[] pics = {"a.jpg", "b.jpg", "c.jpg"};
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(5L);
        givenParam.setPics(new ArrayList<>(pics.length));
        for(int i=0; i<pics.length; i++){
            givenParam.getPics().add(pics[i]);
        }
//        givenParam.getPics().add("a.jpg"); 한번에 작성
//        givenParam.getPics().add("b.jpg");

        List<FeedPicVo> feedPicListBefore = feedPicTestMapper.selFeedPicListByFeedId(givenParam.getFeedId());
        int actualAffectedRows = feedPicMapper.insFeedPic(givenParam);
        List<FeedPicVo> feedPicListAfter = feedPicTestMapper.selFeedPicListByFeedId(givenParam.getFeedId());


        // 아래 containsAll 이 돌아가는 방식
        List<String> picsList = Arrays.asList(pics);
        for(int i=0; i<picsList.size(); i++){
            String pic = picsList.get(i);
            System.out.printf("%s - contains : %b\n", pic, feedPicListAfter.contains(pic));
//            if(!feedPicListAfter.contains(pic)){
//                return false;
//            }
        }

        assertAll(
                () -> assertEquals(pics.length, actualAffectedRows)
                , () -> assertEquals(0, feedPicListBefore.size())
                , () -> assertEquals(pics.length, feedPicListAfter.size())
                , () -> assertTrue(feedPicListAfter.containsAll(Arrays.asList(pics)))

        );

    }
    @Test
    void insFeedPic1(){
        String [] pics = {"a.jpg", "b.jpg", "c.jpg"};
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(1L);
        givenParam.setPics(new ArrayList<>(pics.length));
        for(String pic: pics){
            givenParam.getPics().add(pic);
        }
        int actualAffectedRows = feedPicMapper.insFeedPic(givenParam);
        assertEquals(pics.length, actualAffectedRows);
    }

    @Test
    void insFeedPic_PicStringLengthMoreThan50_ThrowException(){
        FeedPicDto givenParam = new FeedPicDto();
        givenParam.setFeedId(1L);
        givenParam.setPics(new ArrayList<>(1));
        givenParam.getPics().add("123456789_123456789_123456789_123456789_123456789_12");
        assertThrows(BadSqlGrammarException.class, () -> feedPicMapper.insFeedPic(givenParam));
    }
}