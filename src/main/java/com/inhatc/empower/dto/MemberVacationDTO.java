package com.inhatc.empower.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inhatc.empower.constant.LeaveType;
import com.inhatc.empower.constant.MemberVacationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberVacationDTO {

    private Long vacId; // 휴가 번호
    private String eid; // 사원 ID
    private String memberName;        // 추가
    private String department;        // 추가
    private String position;          // 추가
    private LeaveType vacType; // 휴가 유형
    private MemberVacationStatus vacStatus; // 승인 상태
    private LocalDate vacStartDate; // 시작일
    private LocalDate vacEndDate; // 종료일
    private String vacDescription; // 사유
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regTime;
}
