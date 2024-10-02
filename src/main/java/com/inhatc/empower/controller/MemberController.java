package com.inhatc.empower.controller;

import com.inhatc.empower.dto.MemberSearchDTO;
import com.inhatc.empower.dto.PageRequestDTO;
import com.inhatc.empower.dto.PageResponseDTO;
import com.inhatc.empower.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    // 사용자 조회를 위한 기능
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')") //임시로 권한 설정
    @GetMapping("/list")
    public PageResponseDTO<MemberSearchDTO> list(PageRequestDTO pageRequestDTO,  @RequestParam String option,
                                                 @RequestParam String term){
        log.info("list..........."+ pageRequestDTO);
        return memberService.getMemberList(pageRequestDTO, option, term);
    }

}
