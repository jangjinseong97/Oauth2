package com.green.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class FeedPicIds implements Serializable {
    // Serializable 직렬화, @Embeddable, @EqualsAndHashCode 이 3개는 복합키때 필수
    private Long feedId;
    @Column(length = 50)
    private String pic;
}
