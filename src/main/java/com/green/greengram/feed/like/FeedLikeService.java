package com.green.greengram.feed.like;

import com.green.greengram.config.sercurity.AuthenticationFacade;
import com.green.greengram.feed.like.model.FeedLikeReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedLikeService {
    private final FeedLikeMapper mapper;
    private final AuthenticationFacade authenticationFacade;
    private final FeedLikeRepository feedLikeRepository;

    public int feedLikeToggle(FeedLikeReq p){
        p.setUserId(authenticationFacade.getSignedUserId());
        int res = mapper.delFeedLike(p);
        // 먼저 좋아요를 삭제 해봄 되면 1 안되면 0
        if(res == 0){
            // 삭제가 안되면 없었던 것이므로 ins
            return mapper.insFeedLike(p);
            // 1이 리턴됨(좋아요 등록시)
        }
        return 0; // 0이 리턴됨(좋아요 취소시 즉 위의 mapper.del 로 쿼리문이 실행 됬을 시)
    }
}
