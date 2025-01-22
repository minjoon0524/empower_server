package com.inhatc.empower.controller;

import com.inhatc.empower.dto.MemberAttendanceDTO;
import com.inhatc.empower.dto.PageRequestDTO;
import com.inhatc.empower.dto.PageResponseDTO;
import com.inhatc.empower.service.MemberAttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/member/attendance")
@RequiredArgsConstructor
@Log4j2
//출퇴근 컨트롤러
public class MemberAttendanceController {

    private final MemberAttendanceService memberAttendanceService;

    // 전체 근태 내역 추가(관리자용)
    @GetMapping("/list")
    public PageResponseDTO<MemberAttendanceDTO> getAttendanceList(
            PageRequestDTO pageRequestDTO,
            @RequestParam(name = "option", required = false) String option,
            @RequestParam(name = "term", required = false) String term,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.info("list..........." + pageRequestDTO);
        return memberAttendanceService.getAttendanceList(pageRequestDTO, option, term, startDate, endDate);

    }

    // 로그인한 사용자 근태기록(전체)
    @GetMapping("/{eid}")
    public ResponseEntity<PageResponseDTO<MemberAttendanceDTO>> getOneMemberAttendance(
            PageRequestDTO pageRequestDTO,
            @PathVariable(name="eid") String eid
    ) {

        PageResponseDTO<MemberAttendanceDTO> response = memberAttendanceService.getOneMemberAttendance(pageRequestDTO, eid);
        return ResponseEntity.ok(response);
    }


    // 로그인한 사용자 근태기록(날짜별로)
    @GetMapping("/date/{eid}")
    public ResponseEntity<PageResponseDTO<MemberAttendanceDTO>> getAttendanceByDate(
            PageRequestDTO pageRequestDTO,
            @PathVariable(name = "eid") String eid,
            @RequestParam("selectedDate") LocalDate date
    ) {
        PageResponseDTO<MemberAttendanceDTO> response = memberAttendanceService.getAttendanceByDate(pageRequestDTO, eid, date);
        return ResponseEntity.ok(response);
    }

    // 출근 체크
    @PostMapping("/check-in/{eid}")
    public ResponseEntity<String> checkIn(@PathVariable(name = "eid") String eid) {
        try {
            memberAttendanceService.checkIn(eid);
            return ResponseEntity.ok("출근이 등록되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 퇴근 체크
    @PostMapping("/check-out/{eid}")
    public ResponseEntity<String> checkOut(@PathVariable(name = "eid") String eid) {
        try {
            memberAttendanceService.checkOut(eid);
            return ResponseEntity.ok("퇴근이 등록되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
