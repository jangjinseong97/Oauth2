package com.green.greengramver2.feed;

import com.green.greengramver2.feed.model.FeedGetReq;
import com.green.greengramver2.feed.model.FeedGetRes;
import com.green.greengramver2.feed.model.FeedPicDto;
import com.green.greengramver2.feed.model.FeedPostReq;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedPostReq p);
    int insPicDto1(FeedPicDto p);
    List<FeedGetRes> selFeedList(FeedGetReq p);
}
