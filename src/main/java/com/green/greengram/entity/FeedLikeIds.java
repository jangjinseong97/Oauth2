package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class FeedLikeIds implements Serializable {
    private Long userId;
    private Long feedId;
}
