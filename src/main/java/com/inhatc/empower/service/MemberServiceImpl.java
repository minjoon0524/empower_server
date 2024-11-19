package com.inhatc.empower.service;

import com.inhatc.empower.domain.Member;
import com.inhatc.empower.constant.MemberRole;
import com.inhatc.empower.dto.*;
import com.inhatc.empower.repository.MemberRepository;
import com.inhatc.empower.util.CustomFileUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomFileUtil customFileUtil; // CustomFileUtil 주입
    private final ModelMapper modelMapper;
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
    public String register(MemberAddDTO memberAddDTO) {
        // 회원 추가를 위한 member 객체 생성
        Member member = Member.builder()
                .eid(memberAddDTO.getEid())
                .email(memberAddDTO.getEmail())
                .position(memberAddDTO.getPosition())
                .phone(memberAddDTO.getPhone())
                .department(memberAddDTO.getDepartment())
                .name(memberAddDTO.getName())
                .address(memberAddDTO.getAddress())
                .pw(passwordEncoder.encode(memberAddDTO.getPw()))
                .build();
        // 회원 추가 시 기본 권한 설정
        member.addRole(MemberRole.USER);
        // 회원 저장 후 사원 번호 가져오기
        memberRepository.save(member).getEid();
        return memberRepository.save(member).getEid();
    }


    public MemberProfileDTO get(String eid) {
        Member member = memberRepository.getWithRoles(eid);
        String profileName = member.getProfileName();
        String profileImagePath = null;

        // 프로필 이미지 파일 경로 설정
        if (profileName != null && !profileName.isEmpty()) {
            // getFile 메소드를 활용하여 파일을 확인 및 처리
            ResponseEntity<Resource> response = customFileUtil.getFile(profileName);
            if (response.getStatusCode().is2xxSuccessful()) {
                profileImagePath = profileName;  // 파일이 존재할 경우 해당 파일명 사용
            } else {
                profileImagePath = "default.png";  // 파일이 없을 경우 기본 이미지 사용
            }
        } else {
            profileImagePath = "default.png";  // 프로필 이미지가 없을 경우 기본 이미지 사용
        }
        log.info(member);
        // MemberDTO 객체 생성
        MemberProfileDTO memberDTO = MemberProfileDTO.builder()
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
                .profileImagePath(profileImagePath) // 프로필 이미지 경로 설정
                .roleNames(member.getMemberRoleList().stream()
                        .map(Enum::name)
                        .collect(Collectors.toList()))
                .build();

        return memberDTO;
    }

    @Override
    public void modify(MemberModifyDTO memberModifyDTO, MultipartFile profileName) {
        Member member = memberRepository.getWithRoles(memberModifyDTO.getEid());

        // 프로필 사진 업로드
        if (profileName != null && !profileName.isEmpty()) {
            String savedProfilePictureName = customFileUtil.saveProfilePicture(profileName);
            member.setProfileName(savedProfilePictureName); // 엔티티에 프로필 사진 이름 설정
        }
        modelMapper.map(memberModifyDTO, member);
//        member.changeName(memberModifyDTO.getName());
//        member.changeEmail(memberModifyDTO.getEmail());
//        member.changeHireDate(memberModifyDTO.getHireDate());
//        member.changePw(passwordEncoder.encode(memberModifyDTO.getPw()));
//        member.changeDepartment(memberModifyDTO.getDepartment());
//        member.changePhone(memberModifyDTO.getPhone());
//        member.changeAddress(memberModifyDTO.getAddress());
//        member.changePosition(memberModifyDTO.getPosition());
        memberRepository.save(member);
    }

    @Override
    public void remove(String eid) {
        memberRepository.deleteById(eid);
    }
}
