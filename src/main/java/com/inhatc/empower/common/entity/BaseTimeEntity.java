package com.inhatc.empower.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
//공통으로 사용하는 클래스
//추상클래스(단독으로 객체를 만들지 못 하게 한다.)
//감시를 할 수 있는 용도
@EntityListeners(value = {AuditingEntityListener.class}) // AuditingEntityListener 사용하여 등록이나 수정 시간 자동으로 처리
@MappedSuperclass // 해당클래스를 상속받는 클래스에게 속성만 제공
@Getter
@Setter
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime; // 등록시간 관리

    @LastModifiedDate
    private LocalDateTime updateTime; // 수정시간 관리

}
