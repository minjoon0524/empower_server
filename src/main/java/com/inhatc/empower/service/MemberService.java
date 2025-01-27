package com.inhatc.empower.service;

import com.inhatc.empower.constant.MemberRole;
import com.inhatc.empower.dto.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
public interface MemberService {
    PageResponseDTO<MemberSearchDTO> getMemberList(PageRequestDTO pageRequestDTO, String option, String term);


    //    등록
    String register(MemberAddDTO memberAddDTO);
    //    조회
    MemberProfileDTO get(String eid);
    //    수정
    void modify(MemberModifyDTO memberModifyDTO, MultipartFile profileName);
    //    삭제
    void remove(String eid);

    //    권한
    List<MemberRole> grantUser(String eid);

    // 조회(QueryDSL)
    PageResponseDTO<MemberSearchDTO> getMemberLists(PageRequestDTO pageRequestDTO, String searchType,String keyword);


}
