package com.green.greengramver2.feed;

import com.green.greengramver2.common.MyFileUtils;
import com.green.greengramver2.feed.model.FeedPicDto;
import com.green.greengramver2.feed.model.FeedPostReq;
import com.green.greengramver2.feed.model.FeedPostRes;
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
    private final MyFileUtils myFileUtils;

    @Transactional
    // sql에 넣을 때 오토커밋을 자동으로 끄고 에러가 없을 때만 커밋을 해주는 에노테이션
    public FeedPostRes postFeed(List<MultipartFile> pics,
                                FeedPostReq p){
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
}
