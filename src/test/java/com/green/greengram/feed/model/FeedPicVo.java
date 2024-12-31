package com.green.greengram.feed.model;

import lombok.Getter;

//setter 메소드가 없어서 Reflection Api 를 이용하여 데이터가 대입이 될건데
// 객체생성이 무조건 되어야 한다. > 기본생성자 필수
@Getter
public class FeedPicVo {
    private long feedId;
    private String pic;
    private String createdAt;
}
