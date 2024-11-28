package com.green.greengramver2.feed;

import com.green.greengramver2.common.MyFileUtils;
import com.green.greengramver2.feed.model.FeedPicDto;
import com.green.greengramver2.feed.model.FeedPostReq;
import com.green.greengramver2.feed.model.FeedPostRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    public FeedPostRes postFeed(List<MultipartFile> pics,
                                FeedPostReq p){
        int result = feedMapper.insFeed(p);
        long feedId = p.getFeedId();

        String picFolderPath = String.format("feed/%d", feedId);
        myFileUtils.makeFolder(picFolderPath);

        FeedPostRes res = new FeedPostRes();
        res.setFeedId(feedId);
        res.setPics(new ArrayList<>());

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
            res.getPics().add(file);
            feedPicDto.setPic(file);
            feedMapper.insPicDto(feedPicDto);
        }
        // 사진 저장
        // 저장한 사진 res pic에 등록

        return res;
    }
}