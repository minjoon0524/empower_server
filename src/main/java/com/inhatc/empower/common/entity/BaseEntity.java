package com.inhatc.empower.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(value = {AuditingEntityListener.class}) // AuditingEntityListener 사용하여 등록이나 수정 시간 자동으로 처리
@MappedSuperclass // 해당클래스를 상속받는 클래스에게 속성만 제공
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(updatable = false)
    private String createBy; //등록자

    @LastModifiedBy
    private String modifiedBy; // 수정자

}
