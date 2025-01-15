package com.inhatc.empower.repository;

import com.inhatc.empower.constant.MemberAttendanceStatus;
import com.inhatc.empower.domain.MemberAttendance;
import com.inhatc.empower.service.MemberAttendanceService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
public class MemberAttendanceRepositoryTest {
    @Autowired
    private MemberAttendanceCustomRepository memberAttendanceCustomRepository;

    @Autowired
    private MemberAttendanceService memberAttendanceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("근태관리 다중조건 테스트(QueryDSL)")
    public void findMemberAttendanceByDateAndOptionsTest() {
        System.out.println("============ 근태관리 다중조건 테스트(QueryDSL) =============");

        // 테스트용 데이터 준비
        LocalDateTime start = LocalDateTime.now().minusDays(100); // 최근 30일간의 데이터
        LocalDateTime end = LocalDateTime.now(); // 현재 시간까지
        String name = ""; // 테스트 이름 (null로도 테스트 가능)
        String department = ""; // 테스트 부서 (null로도 테스트 가능)
        String position = "사원"; // 테스트 직책 (null로도 테스트 가능)
        String eid="";
        MemberAttendanceStatus status = MemberAttendanceStatus.CHECKED_IN; // 테스트 상태
        Pageable pageable = PageRequest.of(0, 10); // 첫 번째 페이지, 10개 데이터

        // 다중 조건 검색 수행
        Page<MemberAttendance> result = memberAttendanceCustomRepository.findMemberAttendanceByDateAndOptions(
                start, end, name, department, position, eid, pageable
        );

        // 결과 검증
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty(); // 결과가 존재해야 함
        assertThat(result.getContent().size()).isLessThanOrEqualTo(10); // 페이지 크기 확인

        // 출력
        List<MemberAttendance> attendanceList = result.getContent();
        attendanceList.forEach(attendance -> {
            System.out.println("Attendance ID: " + attendance.getEmployeeId());
            System.out.println("Name: " + attendance.getMember().getName());
            System.out.println("Department: " + attendance.getMember().getDepartment());
            System.out.println("Position: " + attendance.getMember().getPosition());
            System.out.println("Status: " + attendance.getStatus());
            System.out.println("Check-in Time: " + attendance.getCheckInTime());
        });

        System.out.println("Total Elements: " + result.getTotalElements());
        System.out.println("Total Pages: " + result.getTotalPages());
    }
}
