package com.inhatc.empower.service;

import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberRole;
import com.inhatc.empower.dto.MemberSearchDTO;
import com.inhatc.empower.dto.PageRequestDTO;
import com.inhatc.empower.dto.PageResponseDTO;
import com.inhatc.empower.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;


    @Override
    public PageResponseDTO<MemberSearchDTO> getMemberList(PageRequestDTO pageRequestDTO, String option, String term) {
        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by("email").descending());

        // Member 리스트 조회
        Page<Member> result;
        switch (option) {
            case "name":
                result = memberRepository.findByNameContaining(term, pageable);
                break;
            case "department":
                result = memberRepository.findByDepartmentContaining(term, pageable);
                break;
            case "position":
                result = memberRepository.findByPositionContaining(term, pageable);
                break;
            case "email":
                result = memberRepository.findByEmailContaining(term, pageable);
                break;
            case "tel":
                result = memberRepository.findByPhoneContaining(term, pageable);
                break;
            default:
                result = memberRepository.getMemberList(pageable);
        }

        // MemberSearchDTO 리스트 생성
        List<MemberSearchDTO> dtoList = result.getContent().stream().map(member -> {
            return MemberSearchDTO.builder()
                    .eid(member.getEid())
                    .name(member.getName())
                    .pw(member.getPw())
                    .department(member.getDepartment())
                    .email(member.getEmail())
                    .phone(member.getPhone())
                    .address(member.getAddress())
                    .position(member.getPosition())
                    .hireDate(member.getHireDate())
                    .memberCheck(member.isMemberCheck())
                    .roleNames(member.getMemberRoleList()
                            .stream()
                            .map(MemberRole::name)
                            .collect(Collectors.toList()))
                    .build();
        }).collect(Collectors.toList());

        // 전체 회원 수
        long totalCount = result.getTotalElements();

        // PageResponseDTO 반환
        return PageResponseDTO.<MemberSearchDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }



}
