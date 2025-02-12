package com.green.greengram.feed;

import com.green.greengram.entity.Feed;
import com.green.greengram.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 있어도 되고 없어도 됨
public interface FeedRepository extends JpaRepository<Feed,Long> {
    Optional<Feed> findByFeedIdAndWriterUser(Long feedId, User user);

    int deleteByFeedIdAndWriterUser(Long feedId, User user);
    // 위 2개의 경우는 selete 이후 delete, update 하기 때문에 성능상으로는 비추천함 > 단 안정적임

    //JPQL java persistence query language
    @Modifying // 이 에노티이션이 있어야 delete or update 없으면 무조건 select jpql, 리턴은 void 나 int
    @Query("delete from Feed f where f.feedId=:feedId and f.writerUser.userId =:writerUserId")
    // =: 부분이 xml에서 =#{} 의 느낌
    // 내부의 from 뒤의 feed는 Feed로 해야됨(대문자로 시작) - 클래스명 작성 해야함
    int deleteFeed(@Param("feedId") Long feedId, @Param("writerUserId") Long writerUserId);
    // 쿼리문을 직접 적어줌으로 delete만 바로 적용 > @Param 의 경우 위와 이름이 같다면 생략해도 무관함

    @Modifying
    @Query(value = "delete from Feed f where f.feed_id=:feedId and f.writerUser.user_id =:writerUserId",nativeQuery = true)
    int deleteFeedSql(Long feedId, Long writerUserId);
    /*
    feedId=1, writerUserId=2 라는 가정하에 아래의 sql이 만들어짐

    delete from feed f
    where f.feed_id =1
    and f.user_id=2

     */

}
