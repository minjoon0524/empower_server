package com.inhatc.empower.repository;

import com.inhatc.empower.constant.MemberAttendanceStatus;
import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberAttendanceRepository extends JpaRepository<MemberAttendance, Long> {
    @Query("select m from MemberAttendance m where m.member.eid = :eid order by m.checkInTime desc")
    Page<MemberAttendance> findByMemberEidOrderByCheckInTimeDesc(@Param("eid") String eid, Pageable pageable);

    List<MemberAttendance> findByMember(Member member);
    Page<MemberAttendance> findByMemberAndCheckInTimeBetween(Member member, LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("select m from MemberAttendance m")
    Page<MemberAttendance> getMemberAttendance(Pageable pageable);

    Page<MemberAttendance> findByCheckInTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MemberAttendance> findByMember_NameContainingAndCheckInTimeBetween(String name, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<MemberAttendance> findByMember_DepartmentContainingAndCheckInTimeBetween(String department, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MemberAttendance> findByMember_PositionContainingAndCheckInTimeBetween(String position, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MemberAttendance> findByMember_EidContainingAndCheckInTimeBetween(String email, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<MemberAttendance> findByStatusAndCheckInTimeBetween(MemberAttendanceStatus status, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // 날짜가 없는 경우에 대한 메서드 추가
    Page<MemberAttendance> findByMember_NameContaining(String name, Pageable pageable);

    Page<MemberAttendance> findByMember_DepartmentContaining(String department, Pageable pageable);

    Page<MemberAttendance> findByMember_PositionContaining(String position, Pageable pageable);



    Page<MemberAttendance> findByStatus(MemberAttendanceStatus status, Pageable pageable);

    Page<MemberAttendance> findByMember_EidContaining(String eid, Pageable pageable);
}
