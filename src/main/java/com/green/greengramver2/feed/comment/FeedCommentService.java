package com.green.greengramver2.feed.comment;

import com.green.greengramver2.feed.FeedMapper;
import com.green.greengramver2.feed.comment.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        // 실행 후
    }

    public FeedCommentGetRes getFeedComment(FeedCommentGetReq p){
        // 실행 시 튜플이 빠진상태로 시작
        FeedCommentGetRes res = new FeedCommentGetRes();
//        if(p.getPage() < 2){
//            res.setCommentList(new ArrayList<>());
//            // swagger에서 1페이지가 이걸로 인해 나오지 않음 > 지운다고 swagger에서 1페이지 말고 바뀔게 있나?
//            return res;
//        }
        List<FeedCommentDto> commentList = feedMapper.selFeedCommentListBy(p);
        // 여기의 호출로 인해 이미 21개까지 제한이므로

        res.setCommentList(commentList);
//        res.setMoreComment(false);
//        if(commentList.size()==p.getSize()){
//            commentList.remove(commentList.size() - 1);
//            res.setMoreComment(true);
//        }

        res.setMoreComment(commentList.size()==p.getSize());
        if(res.isMoreComment()){
            commentList.remove(commentList.size() - 1);
        }
        log.info("res: {}",res);
        return res;
    }
}
