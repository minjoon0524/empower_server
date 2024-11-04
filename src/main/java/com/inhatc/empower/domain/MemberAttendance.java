package com.inhatc.empower.domain;

import com.inhatc.empower.common.entity.BaseEntity;
import com.inhatc.empower.constant.MemberAttendanceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eid", referencedColumnName = "eid")
    private Member member; // Member와 조인

    private LocalDateTime checkInTime; // 출근 시간
    private LocalDateTime checkOutTime; // 퇴근 시간

    @Enumerated(EnumType.STRING)
    private MemberAttendanceStatus status;

    // 출근 체크
    public void checkIn(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
        this.status = MemberAttendanceStatus.CHECKED_IN;
    }

    // 퇴근 체크
    public void checkOut(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
        this.status = MemberAttendanceStatus.CHECKED_OUT;
    }

    public void updateStatus(MemberAttendanceStatus status) {
        this.status = status;
    }
}
