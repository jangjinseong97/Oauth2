package com.green.greengramver2.feed;

import com.green.greengramver2.common.MyFileUtils;
import com.green.greengramver2.feed.comment.FeedCommentMapper;
import com.green.greengramver2.feed.comment.model.FeedCommentDto;
import com.green.greengramver2.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comment.model.FeedCommentGetRes;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import com.green.greengramver2.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedPicsMapper feedPicsMapper;
    private final FeedCommentMapper feedCommentMapper;
    private final MyFileUtils myFileUtils;

    @Transactional
    // sql에 넣을 때 오토커밋을 자동으로 끄고 에러가 없을 때만 커밋을 해주는 에노테이션
    public FeedPostRes postFeed(List<MultipartFile> pics,
                                FeedPostReq p){
        log.info("service {}",p.toString());
        int result = feedMapper.insFeed(p);
        long feedId = p.getFeedId();

        String picFolderPath = String.format("feed/%d", feedId);
        myFileUtils.makeFolder(picFolderPath);


//        for(int i=0; i<pics.size();i++){
//            String file = myFileUtils.makeRandomFileName(pics.get(i));
//            String filePath = String.format("%s/%s",picFolderPath,file);
//
//            try {
//                myFileUtils.transferTo(pics.get(i), filePath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            res.getPics().add(file);
//            feedPicDto.setPic(file);
//            feedMapper.insPicDto(feedPicDto);
//        }



        List<String> picNames = new ArrayList<>(pics.size());

        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setFeedId(feedId);
        for(MultipartFile pic : pics){
            String file = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s",picFolderPath,file);
            try{
                myFileUtils.transferTo(pic,filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            picNames.add(file);
//      feedPicDto.setPic(file);
//      feedMapper.insPicDto(feedPicDto);
//      FeedPicDto 를 list로 바꾸기 이전엔 안에서 생성 후 호출
        }

        feedPicDto.setPics(picNames);
        // list로 바꿨으므로 값을 담고나서 밖에서 생성 해야 된다.
        int resultPics = feedPicsMapper.insFeedPics(feedPicDto);

//        FeedPostRes res = new FeedPostRes();
//        res.setFeedId(feedId);
//        res.setPics(new ArrayList<>(pics.size()));
//        res.setPics(picNames);
//
//        return res;

        return FeedPostRes.builder().
                feedId(feedId).pics(picNames).build();

    }

    public List<FeedGetRes> getFeedList(FeedGetReq p){
        // n+1 이슈 발생?
        List<FeedGetRes> res = feedMapper.selFeedList(p);
        for(FeedGetRes r : res){
//            List<String> list = feedPicsMapper.selFeedPicList(r.getFeedId());
//            r.setPics(list);
            r.setPics(feedPicsMapper.selFeedPicList(r.getFeedId()));

            FeedCommentGetReq commentGetReq = new FeedCommentGetReq(r.getFeedId(),3,0);
//            commentGetReq.setPage(1);
//            commentGetReq.setSize();
            commentGetReq.setFeedId(r.getFeedId());

            List<FeedCommentDto> commentList = feedCommentMapper.selFeedCommentListBy(commentGetReq);
            FeedCommentGetRes commentGetRes = new FeedCommentGetRes();
            commentGetRes.setCommentList(commentList);
//            boolean a=false;
//            if(commentList.size()==4){
//                a = true;
//            }
            commentGetRes.setMoreComment(commentList.size()==commentGetReq.getSize());
            // 굳이 if 문을 안거치고한번에 넣어도 됨
//            commentGetRes.setMoreComment(commentGetRes.getCommentList().size()==4);
            // 어쳐피 commentList를 넣어준거니 동일한 자료

            if(commentGetRes.isMoreComment()){
                commentList.remove(commentList.size()-1);
            }
            // 4개라면 3개만 출력하기 위하여 하나를 빼는 것
            r.setComment(commentGetRes);
        }
        return res;
    }
}
