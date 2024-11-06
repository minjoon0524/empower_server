package com.inhatc.empower.dto;

import com.inhatc.empower.constant.LeaveType;
import com.inhatc.empower.constant.MemberVacationStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberVacationDTO {

    private Long vacId; // 휴가 번호
    private String eid; // 사원 ID
    private LeaveType vacType; // 휴가 유형
    private MemberVacationStatus vacStatus; // 승인 상태
    private LocalDate vacStartDate; // 시작일
    private LocalDate vacEndDate; // 종료일
    private String vacDescription; // 사유
}
