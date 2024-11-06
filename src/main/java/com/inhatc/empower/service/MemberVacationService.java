package com.inhatc.empower.service;

import com.inhatc.empower.constant.MemberVacationStatus;
import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberVacation;
import com.inhatc.empower.dto.MemberAttendanceDTO;
import com.inhatc.empower.dto.MemberVacationDTO;
import com.inhatc.empower.dto.PageRequestDTO;
import com.inhatc.empower.dto.PageResponseDTO;

import java.util.List;

public interface MemberVacationService {
    // 1. 휴가 리스트 조회 (특정 회원의 휴가 목록을 조회할 경우 memberId 파라미터 추가)
    //List<Member> getMemberVacationsList(String memberId);
    PageResponseDTO<MemberVacationDTO> getMemberVacationsList(PageRequestDTO pageRequestDTO, String eid);

    // 2. 휴가 상세정보 조회 (vacId 기준으로 특정 휴가 조회)
    MemberVacationDTO detailsVacation(Long vacId);

    // 3. 휴가 등록 (휴가 정보를 DTO로 받아 등록)
    Long insertVacation(MemberVacationDTO memberVacationDTO);

    // 4. 휴가 수정 (수정할 휴가 정보의 vacId와 업데이트할 내용을 포함한 DTO)
    void modifyVacation(MemberVacationDTO memberVacationDTO);

    // 5. 휴가 삭제 (삭제할 휴가 ID를 받아 처리)
    void deleteVacation(Long vacId);

    // 6. 휴가 승인 (승인할 휴가 ID와 상태를 지정)
    void approveVacation(Long vacId, MemberVacationStatus status);


}
