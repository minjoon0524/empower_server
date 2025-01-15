package com.inhatc.empower.repository;

import com.inhatc.empower.constant.MemberAttendanceStatus;
import com.inhatc.empower.domain.MemberAttendance;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MemberAttendanceCustomRepository{
// 날짜 및 옵션 선택 관련 메소드 구현
Page<MemberAttendance> findMemberAttendanceByDateAndOptions(LocalDateTime start, LocalDateTime end,
                                                            String name, String department, String position,
                                                            String eid, Pageable pageable);


}
