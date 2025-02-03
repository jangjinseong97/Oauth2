package com.green.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class UpdatedAt extends CreatedAt {
    @LastModifiedDate // 수정이 됬을 때 현재일시값
    @Column(nullable = false) // 해당 에노테이션은 없어도 자동으로 들어가지만(@Colum까지만) 추가적인 세팅을 위해 넣는 것
    private LocalDateTime updatedAt;

}
