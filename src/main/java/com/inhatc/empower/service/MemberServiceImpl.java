package com.inhatc.empower.service;

import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberRole;
import com.inhatc.empower.dto.*;
import com.inhatc.empower.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public String register(MemberDTO memberDTO) {
        // 회원 추가를 위한 member 객체 생성
        Member member = Member.builder()
                .eid(memberDTO.getEid())
                .email(memberDTO.getEmail())
                .position(memberDTO.getPosition())
                .phone(memberDTO.getPhone())
                .department(memberDTO.getDepartment())
                .name(memberDTO.getName())
                .address(memberDTO.getAddress())
                .pw(passwordEncoder.encode(memberDTO.getPassword()))
                .build();
        // 회원 추가시 기본 권한 설정
        member.addRole(MemberRole.USER);
        // 회원 저장 후 사원 번호 가져오기
        memberRepository.save(member).getEid();
        return memberRepository.save(member).getEid();
    }


    @Override
    public MemberDTO get(String eid) {
        Member member = memberRepository.getWithRoles(eid);
        log.info(member);
        // MemberDTO 객체 생성
        MemberDTO memberDTO = new MemberDTO(
                member.getEid(),
                member.getEmail(),
                member.getPw(),
                member.getName(),
                member.getDepartment(),
                member.getPhone(),
                member.getAddress(),
                member.getPosition(),
                member.getHireDate(),
                member.isMemberCheck(),
                member.getMemberRoleList().stream()
                        .map(Enum::name) // Enum의 name() 메서드를 사용하여 역할 이름 가져오기
                        .collect(Collectors.toList())
        );

        return memberDTO;
    }


    @Override
    public void modify(MemberModifyDTO memberModifyDTO) {
        Member member = memberRepository.getWithRoles(memberModifyDTO.getEid());
        member.changeName(memberModifyDTO.getName());
        member.changeEmail(memberModifyDTO.getEmail());
        member.changeHireDate(memberModifyDTO.getHireDate());
        member.changePw(passwordEncoder.encode(memberModifyDTO.getPw()));
        member.changeDepartment(memberModifyDTO.getDepartment());
        member.changePhone(memberModifyDTO.getPhone());
        member.changeAddress(memberModifyDTO.getAddress());
        member.changePosition(memberModifyDTO.getPosition());
        memberRepository.save(member);
    }

    @Override
    public void remove(String eid) {
        memberRepository.deleteById(eid);
    }


}
