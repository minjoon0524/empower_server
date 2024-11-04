package com.inhatc.empower.service;

import com.inhatc.empower.domain.Member;
import com.inhatc.empower.dto.MemberAttendanceDTO;
import com.inhatc.empower.dto.MemberSearchDTO;
import com.inhatc.empower.dto.PageRequestDTO;
import com.inhatc.empower.dto.PageResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional
public interface MemberAttendanceService {
    public PageResponseDTO<MemberAttendanceDTO> getOneMemberAttendance(PageRequestDTO pageRequestDTO, String eid);
    public PageResponseDTO<MemberAttendanceDTO> getAttendanceByDate(PageRequestDTO pageRequestDTO, String eid, LocalDate date);
    public PageResponseDTO<MemberAttendanceDTO> getAttendanceList(PageRequestDTO pageRequestDTO, String option, String term, LocalDate date);
    public void checkIn(String eid);
    public void checkOut(String eid);
}
