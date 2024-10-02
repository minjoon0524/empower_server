package com.inhatc.empower.service;

import com.inhatc.empower.dto.MemberSearchDTO;
import com.inhatc.empower.dto.PageRequestDTO;
import com.inhatc.empower.dto.PageResponseDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {
    PageResponseDTO<MemberSearchDTO> getMemberList(PageRequestDTO pageRequestDTO, String option, String term);

}
