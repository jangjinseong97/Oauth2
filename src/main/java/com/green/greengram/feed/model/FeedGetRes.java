package com.green.greengram.feed.model;

import com.green.greengram.feed.comment.model.FeedCommentDto;
import com.green.greengram.feed.comment.model.FeedCommentGetRes;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자 생성
@EqualsAndHashCode
public class FeedGetRes {
    private long feedId;
    private long writerUserId;
    private String writerNm;
    private String writerPic;
    private String contents;
    private String location;
    private String createdAt;
    private int isLike;

    private List<String> pics;
    private FeedCommentGetRes  comment;

    public FeedGetRes(FeedWithPicCommentDto dto) {
        this.feedId = dto.getFeedId();
        this.writerUserId = dto.getWriterUserId();
        this.writerNm = dto.getWriterNm();
        this.writerPic = dto.getWriterPic();
        this.contents = dto.getContents();
        this.location = dto.getLocation();
        this.createdAt = dto.getCreatedAt();
        this.isLike = dto.getIsLike();
        this.pics = dto.getPics();
        this.comment = new FeedCommentGetRes();
//        if(dto.getCommentList().size() == 4) {
//            this.comment.setMoreComment(true);
//            dto.getCommentList().remove(dto.getCommentList().size() - 1);
//        }
//        this.comment.setCommentList(dto.getCommentList());
//    }
        List<FeedCommentDto> list = dto.getCommentList();
        if (list == null) {
            comment.setCommentList(new ArrayList<>());
        }
        comment.setMoreComment(list.size() == 4);
        if (list.size() == 4) {
            list.remove(list.size() - 1);
        }
        comment.setCommentList(list);
    }
}

