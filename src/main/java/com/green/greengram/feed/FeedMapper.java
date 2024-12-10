package com.green.greengram.feed;

import com.green.greengram.feed.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(FeedPostReq p);
    int insPicDto1(FeedPicDto p);
    List<FeedGetRes> selFeedList(FeedGetReq p);
    int delFeedLikeAndFeedCommentAndFeedPic(FeedDelReq p);
    int delFeed(FeedDelReq p);
}
