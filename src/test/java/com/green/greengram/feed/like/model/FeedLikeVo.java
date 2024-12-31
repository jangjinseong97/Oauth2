package com.green.greengram.feed.like.model;


import lombok.*;

@Getter
//@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
// immutable(불변성) 하게 만들고 싶으면 setter를 뺴야된다.
public class FeedLikeVo {
    private long feedId;
    private long userId;
    private String createdAt;
    // 오버라이딩 부모가 가진 메소드중 선언부가 똑같은 경우
}
