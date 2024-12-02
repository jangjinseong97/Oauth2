package com.green.greengramver2.feed.comment;

import com.green.greengramver2.feed.FeedMapper;
import com.green.greengramver2.feed.comment.model.FeedCommentDelReq;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class FeedCommentService {
    private final FeedCommentMapper feedMapper;
    public long insFeedComment(FeedCommentPostReq p){
        int result = feedMapper.insFeedComment(p);
        //comment 에 추가로 사진

        //comment 좋아요는 따로 comment의 pk와 userId로 하위에

        return p.getFeedCommentId();
    }
    public int delFeedComment(FeedCommentDelReq p){
        return feedMapper.delFeedComment(p);
    }
}
