package com.inhatc.empower.service;

import com.inhatc.empower.constant.MemberAttendanceStatus;
import com.inhatc.empower.constant.MemberRole;
import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberAttendance;
import com.inhatc.empower.dto.MemberAttendanceDTO;
import com.inhatc.empower.dto.MemberSearchDTO;
import com.inhatc.empower.dto.PageRequestDTO;
import com.inhatc.empower.dto.PageResponseDTO;
import com.inhatc.empower.repository.MemberAttendanceRepository;
import com.inhatc.empower.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberAttendanceServiceImpl implements MemberAttendanceService {
    private final MemberAttendanceRepository memberAttendanceRepository;
    private final MemberRepository memberRepository;


    @Override
    public PageResponseDTO<MemberAttendanceDTO> getOneMemberAttendance(PageRequestDTO pageRequestDTO, String eid) {
        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("checkInTime").descending());

        // 특정 회원 조회
        // 회원 조회
        Member member = memberRepository.findById(eid)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));


        // 해당 회원의 출퇴근 기록 조회
        Page<MemberAttendance> attendanceRecords = memberAttendanceRepository.findByMemberEidOrderByCheckInTimeDesc(eid, pageable);

        // MemberAttendanceDTO 리스트 생성
        List<MemberAttendanceDTO> dtoList = attendanceRecords.getContent().stream()
                .map(attendance -> MemberAttendanceDTO.builder()
                        .employeeId(attendance.getEmployeeId())
                        .eid(attendance.getMember().getEid())
                        .department(attendance.getMember().getDepartment())
                        .name(attendance.getMember().getName())
                        .checkInTime(attendance.getCheckInTime())
                        .checkOutTime(attendance.getCheckOutTime())
                        .status(attendance.getStatus().name())
                        .build())
                .collect(Collectors.toList());

        // 전체 출퇴근 기록 수
        long totalCount = attendanceRecords.getTotalElements();

        // PageResponseDTO 반환
        return PageResponseDTO.<MemberAttendanceDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public PageResponseDTO<MemberAttendanceDTO> getAttendanceByDate(PageRequestDTO pageRequestDTO, String eid, LocalDate date) {
        // ------- 로그인 한 회원(본인) 근태관리
        // 1. 날짜 초기값은 당일이다.
        // 2. 날짜는 파라메터로 변경이 가능하다.
        // 3. 클라이언트 쪽에선 출근 이력과 퇴근 이력을 따로 관리 할 수 있다.


        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("checkInTime").descending());

        // 회원 조회
        Member member = memberRepository.findById(eid)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        // 조회할 날짜의 시작 및 종료 시간 설정
        LocalDateTime start = date.atStartOfDay(); // 시작 시간
        LocalDateTime end = date.plusDays(1).atStartOfDay(); // 종료 시간

        // 출근 및 퇴근 이력 조회
        Page<MemberAttendance> result = memberAttendanceRepository.findByMemberAndCheckInTimeBetween(member, start, end, pageable);

        // MemberAttendanceDTO 리스트 생성
        List<MemberAttendanceDTO> dtoList = result.getContent().stream().map(attendance ->
                MemberAttendanceDTO.builder()
                        .employeeId(attendance.getEmployeeId())
                        .eid(attendance.getMember().getEid())
                        .name(attendance.getMember().getName())
                        .checkInTime(attendance.getCheckInTime())
                        .checkOutTime(attendance.getCheckOutTime())
                        .status(attendance.getStatus().name())
                        .build()
        ).collect(Collectors.toList());

        // 전체 출퇴근 기록 수
        long totalCount = result.getTotalElements();

        // PageResponseDTO 반환
        return PageResponseDTO.<MemberAttendanceDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
    @Override
    public PageResponseDTO<MemberAttendanceDTO> getAttendanceList(PageRequestDTO pageRequestDTO, String option, String term, LocalDate date) {
        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("checkInTime").descending());

        // MemberAttendance 리스트 조회
        Page<MemberAttendance> result;

        // 날짜와 검색 옵션으로 조회
        if (date != null) {
            LocalDateTime startDateTime = date.atStartOfDay();
            LocalDateTime endDateTime = date.plusDays(1).atStartOfDay();

            // 검색 옵션에 따라 필터링
            if (option != null && term != null && !term.isEmpty()) {
                switch (option) {
                    case "name":
                        result = memberAttendanceRepository.findByMember_NameContainingAndCheckInTimeBetween(term, startDateTime, endDateTime, pageable);
                        break;
                    case "department":
                        result = memberAttendanceRepository.findByMember_DepartmentContainingAndCheckInTimeBetween(term, startDateTime, endDateTime, pageable);
                        break;
                    case "position":
                        result = memberAttendanceRepository.findByMember_PositionContainingAndCheckInTimeBetween(term, startDateTime, endDateTime, pageable);
                        break;
                    case "email":
                        result = memberAttendanceRepository.findByMember_EmailContainingAndCheckInTimeBetween(term, startDateTime, endDateTime, pageable);
                        break;
                    case "status":
                        result = memberAttendanceRepository.findByStatusAndCheckInTimeBetween(MemberAttendanceStatus.valueOf(term.toUpperCase()), startDateTime, endDateTime, pageable);
                        break;
                    default:
                        result = memberAttendanceRepository.findByCheckInTimeBetween(startDateTime, endDateTime, pageable);
                }
            } else {
                // 검색 옵션이 비어 있는 경우, 날짜에 맞는 모든 출퇴근 기록 조회
                result = memberAttendanceRepository.findByCheckInTimeBetween(startDateTime, endDateTime, pageable);
            }
        } else {
            // 날짜가 주어지지 않은 경우 모든 출퇴근 기록 조회
            result = memberAttendanceRepository.getMemberAttendance(pageable);
        }

        // MemberAttendanceDTO 리스트 생성
        List<MemberAttendanceDTO> dtoList = result.getContent().stream().map(attendance ->
                MemberAttendanceDTO.builder()
                        .employeeId(attendance.getEmployeeId())
                        .eid(attendance.getMember().getEid())
                        .department(attendance.getMember().getDepartment())
                        .name(attendance.getMember().getName())
                        .checkInTime(attendance.getCheckInTime())
                        .checkOutTime(attendance.getCheckOutTime())
                        .status(attendance.getStatus().name())
                        .build()
        ).collect(Collectors.toList());

        // 전체 출퇴근 기록 수
        long totalCount = result.getTotalElements();

        // PageResponseDTO 반환
        return PageResponseDTO.<MemberAttendanceDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }


    @Override
    public void checkIn(String eid) {
        // 회원 확인
        Member member = memberRepository.findById(eid)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        // 오늘 출근 기록 조회
        List<MemberAttendance> attendanceRecords = memberAttendanceRepository.findByMember(member);
        boolean hasCheckedInToday = attendanceRecords.stream()
                .anyMatch(attendance -> attendance.getCheckInTime().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()));

//        log.info(LocalDateTime.now());
        // 출근이 안 됐다면, 출근 등록
        if (!hasCheckedInToday) {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
            MemberAttendance attendance = MemberAttendance.builder()
                    .member(member)
                    .checkInTime(now)
                    .status(now.isAfter(now.toLocalDate().atTime(9, 0)) ? MemberAttendanceStatus.LATE : MemberAttendanceStatus.CHECKED_IN) // 09:00 이후에 출근하면 지각
                    .build();
            memberAttendanceRepository.save(attendance);
        } else {
            throw new RuntimeException("오늘은 이미 출근하셨습니다.");
        }
    }


    @Override
    public void checkOut(String eid) {
        // 회원 조회
        Member member = memberRepository.findById(eid)
                .orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));

        // 해당 회원의 출근 기록 조회
        List<MemberAttendance> attendanceRecords = memberAttendanceRepository.findByMember(member);

        // 오늘 출근 기록 확인
        MemberAttendance todayAttendance = attendanceRecords.stream()
                .filter(attendance -> attendance.getCheckInTime().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("오늘 출근 기록이 없습니다."));

        // 출근 기록 확인 및 퇴근을 이미 한 경우 체크
        if (todayAttendance.getCheckOutTime() != null) {
            throw new RuntimeException("이미 퇴근하셨습니다.");
        }

        // 퇴근을 안 했을 경우 퇴근 등록
        todayAttendance.checkOut(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        memberAttendanceRepository.save(todayAttendance);

    }
}
