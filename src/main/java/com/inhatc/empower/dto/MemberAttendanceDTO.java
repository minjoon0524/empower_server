package com.inhatc.empower.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberAttendanceDTO {

    private int employeeId;
    private String eid; // 사번
    private String name; // 회원 이름
    private String department;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkInTime; // 출근 시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkOutTime; // 퇴근 시간

    private String status; // 출근 상태 (CHECKED_IN, CHECKED_OUT, LATE 등)
}
