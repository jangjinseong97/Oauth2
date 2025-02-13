package com.green.greengram.entity;

import com.green.greengram.config.sercurity.oauth.userinfo.SignInProviderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity // 테이블을 만들고 DML때 사용
@Getter
@Setter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"uid", "providerType"}
                )
        }
)
public class User extends UpdatedAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY auto_increment
    // TABLE 테이블을 만들어서 시퀀스 생성(테이블에 값을 pk로 쓰고 해당 테이블에 값을 +1)
    private Long userId;

    @Column(nullable = false)
    private SignInProviderType providerType;

    @Column(nullable = false, length = 30)
    private String uid;

    @Column(nullable = false, length = 100)
    private String upw;
    @Column(length = 30)
    private String nickName;
    @Column(length = 50)
    private String pic;


}
