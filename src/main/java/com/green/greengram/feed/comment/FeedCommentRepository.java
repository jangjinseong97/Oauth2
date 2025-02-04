package com.green.greengram.feed.comment;

import com.green.greengram.entity.FeedComment;
import com.green.greengram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    int deleteByFeedCommentIdAndUser(Long feedId, User user);
    @Modifying
    @Query("delete from FeedComment f where f.feedCommentId=:feedCommentId and f.user.userId=:userId")
    int deleteFeedComment(Long feedCommentId, Long userId);
}
