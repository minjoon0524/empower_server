package com.inhatc.empower.service;

import com.inhatc.empower.dto.MemberAttendanceDTO;
import com.inhatc.empower.dto.PageRequestDTO;
import com.inhatc.empower.dto.PageResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
public interface MemberAttendanceService {
    PageResponseDTO<MemberAttendanceDTO> getOneMemberAttendance(PageRequestDTO pageRequestDTO, String eid);
    PageResponseDTO<MemberAttendanceDTO> getAttendanceByDate(PageRequestDTO pageRequestDTO, String eid, LocalDate date);
    PageResponseDTO<MemberAttendanceDTO> getAttendanceList(PageRequestDTO pageRequestDTO, String option, String term, LocalDate startDate,LocalDate endDate);
    void checkIn(String eid);
    void checkOut(String eid);
}
