package com.green.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass // entity 부모역할
@EntityListeners(AuditingEntityListener.class) // 이벤트 연결(binding), insert가 될때 현재일시값을 넣자.
public class CreatedAt {

    @CreatedDate //insert가 되었을 때 현재일시값을 넣음 이게 작동하려면 위의 @EntityListeners가 필요
    @Column(nullable = false) // 해당 에노테이션은 없어도 자동으로 들어가지만(@Colum까지만) 추가적인 세팅을 위해 넣는 것
    private LocalDateTime createdAt;
    // date와 time 둘다 저장 가능
    // 이전 버전이면 date 따로 time 따로
}
