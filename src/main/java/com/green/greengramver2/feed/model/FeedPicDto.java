package com.green.greengramver2.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedPicDto {
    private List<String> pics;
    private long feedId;
}
