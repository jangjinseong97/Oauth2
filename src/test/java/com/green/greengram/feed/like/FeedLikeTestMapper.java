package com.green.greengram.feed.like;


import com.green.greengram.feed.like.model.FeedLikeReq;
import com.green.greengram.feed.like.model.FeedLikeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedLikeTestMapper {
    @Select("select * from feed_like where feed_id =#{feedId} and user_id = #{userId}")
    FeedLikeVo selFeedLikeByFeedIdAndUserId(FeedLikeReq p);

    @Select("select * from feed_like")
    List<FeedLikeVo> selFeedLikeAll();
}
