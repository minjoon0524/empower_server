package com.inhatc.empower.service;

import com.inhatc.empower.domain.Member;
import com.inhatc.empower.dto.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Transactional
public interface MemberService {
    PageResponseDTO<MemberSearchDTO> getMemberList(PageRequestDTO pageRequestDTO, String option, String term);


    //    등록
    String register(MemberAddDTO memberAddDTO);
    //    조회
    MemberDTO get(String eid);
    //    수정
    void modify(MemberModifyDTO memberModifyDTO, MultipartFile profileName);
    //    삭제
    void remove(String eid);


default MemberDTO entityToDto(Member member) {
    MemberDTO memberDTO = new MemberDTO();
    memberDTO.setEid(member.getEid());
    memberDTO.setName(member.getName());
    memberDTO.setPw(member.getPw());
    memberDTO.setDepartment(member.getDepartment());
    memberDTO.setEmail(member.getEmail());
    memberDTO.setPhone(member.getPhone());
    memberDTO.setAddress(member.getAddress());
    memberDTO.setPosition(member.getPosition());
    memberDTO.setHireDate(member.getHireDate());
    memberDTO.setMemberCheck(member.isMemberCheck());

    return memberDTO;
}


    default Member dtoToEntity(MemberDTO memberDTO) {
        return Member.builder()
                .eid(memberDTO.getEid())
                .name(memberDTO.getName())
                .pw(memberDTO.getPw())
                .department(memberDTO.getDepartment())
                .email(memberDTO.getEmail())
                .phone(memberDTO.getPhone())
                .address(memberDTO.getAddress())
                .position(memberDTO.getPosition())
                .hireDate(memberDTO.getHireDate())
                .memberCheck(memberDTO.isMemberCheck())
                .build();
    }



}
