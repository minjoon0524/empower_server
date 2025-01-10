package com.inhatc.empower.service;

import com.inhatc.empower.constant.MemberVacationStatus;
import com.inhatc.empower.dto.*;

public interface MemberVacationService {
    // 1. 전체 휴가 리스트 조회
    PageResponseDTO<MemberVacationDTO> getAllVacationList(PageRequestDTO pageRequestDTO);

    // 2. 휴가 상세정보 조회 (vacId 기준으로 특정 휴가 조회)
    MemberVacationDTO detailsVacation(Long vacId);

    // 3. 휴가 등록 (휴가 정보를 DTO로 받아 등록)
    String insertVacation(MemberVacationModifyDTO memberVacationModifyDTO);

    // 4. 휴가 수정 (수정할 휴가 정보의 vacId와 업데이트할 내용을 포함한 DTO)
    void modifyVacation(MemberVacationDTO memberVacationDTO);

    // 5. 휴가 삭제 (삭제할 휴가 ID를 받아 처리)
    void deleteVacation(Long vacId);

    // 6. 휴가 승인 (승인할 휴가 ID와 상태를 지정)
    String approveVacation(MemberVacationAttendanceDTO memberVacationAttendanceDTO);

    // 7. 로그인한 사용자 휴가리스트 조회
    // 클라이언트는 "eid" 를 보내준다.
    PageResponseDTO<MemberVacationDTO> getOneVacationList(PageRequestDTO pageRequestDTO,String eid);

    // 8. 휴가 상태에 따라 리스트 출력
    PageResponseDTO<MemberVacationDTO> getStatusVacationList(PageRequestDTO pageRequestDTO,String status);

}
