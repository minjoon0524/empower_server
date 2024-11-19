package com.inhatc.empower.domain;

import com.inhatc.empower.common.entity.BaseEntity;
import com.inhatc.empower.constant.LeaveType;
import com.inhatc.empower.constant.MemberVacationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "member")
public class MemberVacation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacId; //휴가 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eid")
    private Member member;

    @Enumerated(EnumType.STRING)
    private LeaveType vacType; //일반(반차), 병가 , 조사(배우자5) ,조사(부모님5),조사(형제1), 예비군
    @Enumerated(EnumType.STRING)
    private MemberVacationStatus vacStatus=MemberVacationStatus.PENDING; //승인상태(관리자)

    private LocalDate vacStartDate; // 시작일
    private LocalDate vacEndDate; // 종료일
    private String vacDescription; //사유

    // 관리자 승인
    public void changeVacStatus(MemberVacationStatus vacStatus) {
        this.vacStatus = vacStatus;
    }
}
