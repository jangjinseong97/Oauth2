package com.green.greengram.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserFollow extends CreatedAt{
    @EmbeddedId
    private UserFollowIds userFollowIds;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    @MapsId("toUserId")
    private User toUser;
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    @MapsId("fromUserId")
    private User fromUser;
}
