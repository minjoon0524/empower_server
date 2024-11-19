package com.inhatc.empower.dto;

import com.inhatc.empower.constant.LeaveType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberVacationModifyDTO {
    private String eid; // 사원 ID
    private LeaveType vacType; // 휴가 유형
    private LocalDate vacStartDate; // 시작일
    private LocalDate vacEndDate; // 종료일
    private String vacDescription; // 사유

}
