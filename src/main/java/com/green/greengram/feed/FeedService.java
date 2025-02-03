package com.green.greengram.feed;

import com.green.greengram.common.MyFileUtils;
import com.green.greengram.common.exception.CustomException;
import com.green.greengram.common.exception.FeedErrorCode;
import com.green.greengram.config.sercurity.AuthenticationFacade;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedPic;
import com.green.greengram.entity.FeedPicIds;
import com.green.greengram.entity.User;
import com.green.greengram.feed.comment.FeedCommentMapper;
import com.green.greengram.feed.comment.model.FeedCommentDto;
import com.green.greengram.feed.comment.model.FeedCommentGetReq;
import com.green.greengram.feed.comment.model.FeedCommentGetRes;
import com.green.greengram.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedPicMapper feedPicMapper;
    private final FeedCommentMapper feedCommentMapper;
    private final MyFileUtils myFileUtils;
    private final AuthenticationFacade authenticationFacade;
    private final FeedRepository feedRepository;
    private final FeedPicRepository feedPicRepository;

    @Transactional
    // sql에 넣을 때 오토커밋을 자동으로 끄고 에러가 없을 때만 커밋을 해주는 에노테이션
    public FeedPostRes postFeed(List<MultipartFile> pics,
                                FeedPostReq p){

        User signedUser = new User();
        signedUser.setUserId(authenticationFacade.getSignedUserId());
//        p.setWriterUserId(authenticationFacade.getSignedUserId());
        Feed feed = new Feed();
        feed.setWriterUser(signedUser);
        feed.setContents(p.getContents());
        feed.setLocation(p.getLocation());
        // 영속성x
        // 영속성 > Spring 과 db 사이 EM(entity maneger 가 있는데 여기 저장되는 상황이 영속성

        log.info("service {}",p);
//        int result = feedMapper.insFeed(p);
//        if(result == 0){
//            throw new CustomException(FeedErrorCode.FAIL_TO_REG);
//        }
        feedRepository.save(feed);
        // 파일 등록
        long feedId = feed.getFeedId();



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

//        return null;
        //test 를 보기위해 아래 주석처리

        List<String> picNames = new ArrayList<>(pics.size());

        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setFeedId(feedId);
        for(MultipartFile pic : pics){
            String file = myFileUtils.makeRandomFileName(pic);
            String filePath = String.format("%s/%s",picFolderPath,file);
            try{
                FeedPicIds ids = new FeedPicIds();
                ids.setFeedId(feedId);
                ids.setPic(file);

                FeedPic feedPic = new FeedPic();
                feedPic.setFeedPicIds(ids);
                feedPic.setFeed(feed);
                feedPicRepository.save(feedPic);

                myFileUtils.transferTo(pic,filePath);
            } catch (IOException e) {
                String delFolderPath = String.format("%s/%s",myFileUtils.getUploadPath(),picFolderPath);
                myFileUtils.deleteFolder(delFolderPath,true);
                throw new CustomException(FeedErrorCode.FAIL_TO_REG);
            }
            picNames.add(file);
//      feedPicDto.setPic(file);
//      feedMapper.insPicDto(feedPicDto);
//      FeedPicDto 를 list로 바꾸기 이전엔 안에서 생성 후 호출
        }

//        feedPicDto.setPics(picNames);
        // list로 바꿨으므로 값을 담고나서 밖에서 생성 해야 된다.
//        int resultPics = feedPicMapper.insFeedPic(feedPicDto);

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

        p.setSignedUserId(authenticationFacade.getSignedUserId());
        // n+1 이슈 발생
        // 아래의 for문으로 인해 각각 하나씩 select 가 사용되는게 문제

        List<FeedGetRes> res = feedMapper.selFeedList(p);
        if(res.size()==0){
            return res;
        }
        for(FeedGetRes r : res){
//            List<String> list = feedPicsMapper.selFeedPicList(r.getFeedId());
//            r.setPics(list);
            r.setPics(feedPicMapper.selFeedPicList(r.getFeedId()));

            FeedCommentGetReq commentGetReq = new FeedCommentGetReq(r.getFeedId(),3,0);

//            commentGetReq.setPage(1);
//            commentGetReq.setSize();
//            commentGetReq.setFeedId(r.getFeedId());

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

    // select 3번으로 줄이기
    public List<FeedGetRes> getFeedList3(FeedGetReq p){

        p.setSignedUserId(authenticationFacade.getSignedUserId());
        // n+1 문제 해결

        // 피드 리스트
        List<FeedGetRes> res = feedMapper.selFeedList(p);
        if(res.size()==0){
            return res;
        }

        // 피드 아이디 리스트가 필요함(피드 사진과 피드댓글의 정보를 위해)
        List<Long> feedIdList = new ArrayList<>(res.size());
        for(FeedGetRes r : res){
            feedIdList.add(r.getFeedId());
        }
        List<Long> feedIds = res.stream().map(FeedGetRes::getFeedId).toList();
        // 위의 for문과 동일 (스트림)
        // 속도면만 보면 for문이 더 좋음


        // 피드와 관련된 사진 리스트
        List<FeedPicSel> feedPicList = feedPicMapper.selFeedPicListByFeedIds(feedIds);
        log.info("feedPicList: {}",feedPicList);

//        int lastIndex = 0;
//        for(FeedGetRes r : res){
//            List<String> pics = new ArrayList<>(2);
//
//            for(int i=lastIndex;i<feedPicList.size();i++){
//                FeedPicSel feedPicsel = feedPicList.get(i);
//                if(r.getFeedId()==feedPicsel.getFeedId()){
//                    pics.add(feedPicsel.getPic());
//                } else {
//                    r.setPics(pics);
//                    lastIndex = i;
//                    break;
//                }
//            }
//        }
//        // 순서가 맞아야됨( mapper xml에서 정렬이 필요
//        log.info("res: {}" ,res);

        Map<Long, List<String>> picHashMap = new HashMap<>();
        // Long 에 따른 List<String> 을 반환
        for(FeedPicSel item : feedPicList){
            long feedId = item.getFeedId();
            if(!picHashMap.containsKey(feedId)){
                // key 값중 for문에서 돌고있는 feedId 가 있는지 물어봄
                // 가장 처음은 무조건 없을거니 새로 객체화를 이때 만들어 줌
                picHashMap.put(feedId, new ArrayList<>(2));
            }
            List<String> pics = picHashMap.get(feedId);
            // feedId에 따른 해쉬맵의 주소값(그에 따른 List<String>)을 받아옴
            pics.add(item.getPic());
            // 위에서 받은 주소값에 item의 사진을 넣어주는 것
        }

        // 피드와 관련된 댓글 리스트
        List<FeedCommentDto> feedCommentDtoList = feedCommentMapper.selFeedCommentListByFeedIds(feedIds);
        Map<Long, FeedCommentGetRes> commentMap = new HashMap<>();
        for(FeedCommentDto dto : feedCommentDtoList){
            long feedId = dto.getFeedId();
            if(!commentMap.containsKey(feedId)){
                FeedCommentGetRes feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
                commentMap.put(feedId, feedCommentGetRes);
            }
            FeedCommentGetRes feedCommentGetRes = commentMap.get(feedId);
            feedCommentGetRes.getCommentList().add(dto);
        }

        for(FeedGetRes r : res){
            r.setPics(picHashMap.get(r.getFeedId()));
            FeedCommentGetRes feedCommentGetRes = commentMap.get(r.getFeedId());

            if(feedCommentGetRes == null){
                feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
            } else if(feedCommentGetRes.getCommentList().size()==4){
                feedCommentGetRes.setMoreComment(true);
                feedCommentGetRes.getCommentList().remove(feedCommentGetRes.getCommentList().size()-1);
            }
            r.setComment(feedCommentGetRes);
        }

        return res;
    }
    // select 2번으로 줄이기
    public List<FeedGetRes> getFeedList2(FeedGetReq p){
        // feed + feed_pic 으로 1번
        // feed_comment 로 1번
        List<FeedGetRes> list = new ArrayList<>(p.getSize());
        List<FeedGetRes> list1 = new ArrayList<>(p.getSize());


        // feed + feed_pic
        List<FeedAndPicDto> feedAndPicDtoList = feedMapper.selFeedListWithPicListFix(p);
        Map<Long, FeedGetRes> feedMap = new LinkedHashMap<>();
        Map<Long, List<String>> feedPics = new LinkedHashMap<>();
        for(FeedAndPicDto dto : feedAndPicDtoList){
            long feedId = dto.getFeedId();
            if(!feedMap.containsKey(feedId)){
                FeedGetRes res = new FeedGetRes();
                feedMap.put(feedId, res);
                feedPics.put(feedId, new ArrayList<>());
                res.setFeedId(dto.getFeedId());
                res.setWriterUserId(dto.getWriterUserId());
                res.setWriterNm(dto.getWriterNm());
                res.setWriterPic(dto.getWriterPic());
                res.setContents(dto.getContents());
                res.setLocation(dto.getLocation());
                res.setIsLike(dto.getIsLike());
                res.setCreatedAt(dto.getCreatedAt());
                feedMap.put(feedId, res);
            }
            feedPics.get(feedId).add(dto.getPic());
            feedMap.get(feedId).setPics(feedPics.get(feedId));
        }
        list.addAll(feedMap.values());

        FeedGetRes res = new FeedGetRes();
        for(FeedAndPicDto dto : feedAndPicDtoList){
            if(res.getFeedId() != dto.getFeedId()){
                res = new FeedGetRes();
                res.setPics(new ArrayList<>());
                // feedId 가 없을때만 새 res 주소값을 주고
                // 그때 비었을 pics를 생성

                list1.add(res);
                // 새로 생성한 res 를 List<FeedGetRes>인 list에 넣어줌
                res.setFeedId(dto.getFeedId());
                res.setWriterUserId(dto.getWriterUserId());
                res.setWriterNm(dto.getWriterNm());
                res.setWriterPic(dto.getWriterPic());
                res.setContents(dto.getContents());
                res.setIsLike(dto.getIsLike());
                res.setLocation(dto.getLocation());
                res.setCreatedAt(res.getCreatedAt());

            }
        }

        List<Long> feedIds = new ArrayList<>(feedAndPicDtoList.size());
        for(FeedAndPicDto r : feedAndPicDtoList){
            feedIds.add(r.getFeedId());
        }

        List<FeedCommentDto> feedCommentDtoList = feedCommentMapper.selFeedCommentListByFeedIds(feedIds);
        Map<Long, FeedCommentGetRes> commentMap = new HashMap<>();
        for(FeedCommentDto dto : feedCommentDtoList){
            long feedId = dto.getFeedId();
            if(!commentMap.containsKey(feedId)){
                FeedCommentGetRes feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
                commentMap.put(feedId, feedCommentGetRes);
            }
            FeedCommentGetRes feedCommentGetRes = commentMap.get(feedId);
            feedCommentGetRes.getCommentList().add(dto);
        }

        for(FeedGetRes r : list1){
            r.setPics(feedPics.get(r.getFeedId()));
            FeedCommentGetRes feedCommentGetRes = commentMap.get(r.getFeedId());

            if(feedCommentGetRes == null){
                feedCommentGetRes = new FeedCommentGetRes();
                feedCommentGetRes.setCommentList(new ArrayList<>());
            } else if(feedCommentGetRes.getCommentList().size()==4){
                feedCommentGetRes.setMoreComment(true);
                feedCommentGetRes.getCommentList().remove(feedCommentGetRes.getCommentList().size()-1);
            }
            r.setComment(feedCommentGetRes);
        }





        return list1;
    }

    // select 1번
    public List<FeedGetRes> getFeedList4(FeedGetReq p){
        List<FeedWithPicCommentDto> dtoList = feedMapper.selFeedWithPicAndCommentList(p);
        List<FeedGetRes> res = new ArrayList<>(dtoList.size());
        for(FeedWithPicCommentDto dto : dtoList){
//            FeedGetRes res1 = new FeedGetRes(dto);
//            res.add(res1);
            res.add(new FeedGetRes(dto));
            // 맨날 깜박하는 바로 집어넣어도 괜찮음
        }
        return res;
    }


    @Transactional
    public void delFeed(FeedDelReq p){
        // 피드와 관련된 정보 삭제
        User signedUser = new User();
        signedUser.setUserId(authenticationFacade.getSignedUserId());
//        Feed feed = feedRepository.findByFeedIdAndWriterUser(p.getFeedId(), signedUser)
//                .orElseThrow(RuntimeException::new);
//                .orElseThrow(() -> new CustomException(FeedErrorCode.FAIL_TO_DELETE));
//        feedRepository.delete(feed);
        int affectedRow = feedRepository.deleteFeed(p.getFeedId(), authenticationFacade.getSignedUserId());
        if(affectedRow == 0){
            throw new CustomException(FeedErrorCode.FAIL_TO_DELETE);
        }


//        int affectedRows = feedMapper.delFeedLikeAndFeedCommentAndFeedPic(p);
//
//        // 피드 자체 삭제
//        int affectedRowsFeed = feedMapper.delFeed(p);
//
//        // 피드관련 폴더 삭제
//        String delPath = String.format("%s/feed/%d",myFileUtils.getUploadPath(),p.getFeedId());
//        myFileUtils.deleteFolder(delPath,true);
//
//        return affectedRowsFeed;
    }
}
