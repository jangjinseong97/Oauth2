package com.green.greengram.feed.comment;

import com.green.greengram.common.exception.CustomException;
import com.green.greengram.common.exception.FeedErrorCode;
import com.green.greengram.config.sercurity.AuthenticationFacade;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedComment;
import com.green.greengram.entity.User;
import com.green.greengram.feed.comment.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class FeedCommentService {
    private final FeedCommentMapper feedMapper;
    private final FeedCommentRepository feedCommentRepository;
    private final AuthenticationFacade authenticationFacade;
    public long insFeedComment(FeedCommentPostReq p){
//        int result = feedMapper.insFeedComment(p);
        //comment 에 추가로 사진

        //comment 좋아요는 따로 comment의 pk와 userId로 하위에

//        return p.getFeedCommentId();
        Feed feed = new Feed();
        feed.setFeedId(p.getFeedId());
        User user = new User();
        user.setUserId(authenticationFacade.getSignedUserId());

        FeedComment feedComment = new FeedComment();
        feedComment.setUser(user);
        feedComment.setFeed(feed);
        feedComment.setComment(p.getComment());
        feedCommentRepository.save(feedComment);
        return feedComment.getFeedCommentId();
    }
    @Transactional
    public void delFeedComment(FeedCommentDelReq p){

        User user = new User();
        user.setUserId(authenticationFacade.getSignedUserId());
        log.info("p: {} , userId : {}",p,user.getUserId());
//        int res = feedCommentRepository.deleteByFeedCommentIdAndUser(p.getFeedCommentId(), user);
        int res = feedCommentRepository.deleteFeedComment(p.getFeedCommentId(), user.getUserId());
        if(res == 0){
            throw new CustomException(FeedErrorCode.FAIL_TO_DEL_COMMENT);
        }
        // 실행 후
    }
    public void delFeedComment2(FeedCommentDelReq p){
                FeedComment feedComment = feedCommentRepository.findById(p.getFeedCommentId()).orElse(null);
                // 그래프탐색 : feedComment 테이블 내용을 가져왔는데 User 테이블 정보를 탐색
        if(feedComment == null|| feedComment.getUser().getUserId() != authenticationFacade.getSignedUserId()){
            // 글이 없거나 있지만 내가 쓴글이 아니면 에러 발송
            throw new CustomException(FeedErrorCode.FAIL_TO_DEL_COMMENT);
        }
        feedCommentRepository.delete(feedComment);
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
