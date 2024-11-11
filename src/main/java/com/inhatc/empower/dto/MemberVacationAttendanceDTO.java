package com.inhatc.empower.dto;

import com.inhatc.empower.constant.MemberVacationStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberVacationAttendanceDTO {
    Long vacId; // 휴가 ID
    private MemberVacationStatus vacStatus; // 승인 상태
}
