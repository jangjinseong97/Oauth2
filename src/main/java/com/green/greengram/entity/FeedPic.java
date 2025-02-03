package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
public class FeedPic extends CreatedAt{
    @EmbeddedId
    private FeedPicIds feedPicIds;

    @ManyToOne
    @MapsId("feedId") //필드(속성)명 써줘야됨
    @JoinColumn(name = "feed_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // 단방향 상태에서 on delete cascade 설정
    // 또한 만들어질 때 있어야 적용(만들고 나서는 적용시켜도 안됨)
    private Feed feed;

}
