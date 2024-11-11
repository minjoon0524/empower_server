package com.inhatc.empower.service;

import com.inhatc.empower.constant.MemberVacationStatus;
import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberAttendance;
import com.inhatc.empower.domain.MemberVacation;
import com.inhatc.empower.dto.*;
import com.inhatc.empower.repository.MemberRepository;
import com.inhatc.empower.repository.MemberVacationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberVacationServiceImpl implements MemberVacationService {

    private final MemberRepository memberRepository;
    private final MemberVacationRepository memberVacationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long insertVacation(MemberVacationDTO memberVacationDTO) {
        // 회원정보 가져오기
        Member member = memberRepository.getWithRoles(memberVacationDTO.getEid());
        // DTO를  Entity로 변환
        MemberVacation memberVacation = modelMapper.map(memberVacationDTO, MemberVacation.class);
        memberVacation.setMember(member);
        memberVacationRepository.save(memberVacation);

        return memberVacationDTO.getVacId();
    }


    @Transactional(readOnly = true)
    @Override
    public PageResponseDTO<MemberVacationDTO> getAllVacationList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("regTime").descending()
        );

        // @EntityGraph를 사용하는 메서드로 변경
        Page<MemberVacation> vacationPage = memberVacationRepository.findAll(pageable);

        List<MemberVacationDTO> dtoList = vacationPage.getContent().stream()
                .map(vacation -> MemberVacationDTO.builder()
                        .vacId(vacation.getVacId())
                        .eid(vacation.getMember().getEid())
                        .memberName(vacation.getMember().getName())
                        .department(vacation.getMember().getDepartment())
                        .position(vacation.getMember().getPosition())
                        .vacType(vacation.getVacType())
                        .vacStatus(vacation.getVacStatus())
                        .vacStartDate(vacation.getVacStartDate())
                        .vacEndDate(vacation.getVacEndDate())
                        .vacDescription(vacation.getVacDescription())
                        .build())
                .collect(Collectors.toList());

        return PageResponseDTO.<MemberVacationDTO>withAll()
                .dtoList(dtoList)
                .totalCount(vacationPage.getTotalElements())
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public MemberVacationDTO detailsVacation(Long vacId) {
        MemberVacationDTO memberVacationDTO=new MemberVacationDTO();

        // 휴가 조회
        MemberVacation memberVacation = memberVacationRepository.findById(vacId).orElseThrow();

        memberVacationDTO.setEid(memberVacation.getMember().getEid());
        memberVacationDTO.setMemberName(memberVacation.getMember().getName());
        memberVacationDTO.setDepartment(memberVacation.getMember().getDepartment());
        memberVacationDTO.setPosition(memberVacation.getMember().getPosition());

        modelMapper.map(memberVacation, memberVacationDTO);

        return memberVacationDTO;
    }


    @Override
    public void modifyVacation(MemberVacationDTO memberVacationDTO) {
        // 휴가 조회
        Optional<MemberVacation> result = memberVacationRepository.findById(memberVacationDTO.getVacId());

        if (result.isPresent()) {
            MemberVacation memberVacation = result.get();
            memberVacation.setVacStatus(memberVacationDTO.getVacStatus());
            memberVacation.setVacStartDate(memberVacationDTO.getVacStartDate());
            memberVacation.setVacEndDate(memberVacationDTO.getVacEndDate());
            memberVacation.setVacDescription(memberVacationDTO.getVacDescription());
            memberVacationRepository.save(memberVacation);
        }else{
            throw new EntityNotFoundException("휴가 : " + memberVacationDTO.getVacId() + " 를 찾을 수 없습니다.");
        }

    }

    @Override
    public void deleteVacation(Long vacId) {
        memberVacationRepository.deleteById(vacId);
    }

    @Override
    public String approveVacation(MemberVacationAttendanceDTO memberVacationAttendanceDTO) {
        // 휴가 조회        
        MemberVacation memberVacation = memberVacationRepository.findById(memberVacationAttendanceDTO.getVacId())
                .orElseThrow(() -> new IllegalArgumentException("해당 휴가를 찾을 수 없습니다."));

        // 승인 상태 변경
        memberVacation.changeVacStatus(memberVacationAttendanceDTO.getVacStatus());

        String result;

        // 승인, 거절 상태에 따른 처리
        if (memberVacationAttendanceDTO.getVacStatus().equals(MemberVacationStatus.APPROVE)) {
            // 승인 처리
            result = "승인 처리 완료";
        } else if (memberVacationAttendanceDTO.getVacStatus().equals(MemberVacationStatus.REJECT)) {
            // 거절 처리
            result = "거절 처리 완료";
        } else {
            // 다른 상태 (예: 대기)
            result = "상태 변경 완료";
        }

        // DB 저장
        memberVacationRepository.save(memberVacation);

        return result;
    }


    @Override
    public PageResponseDTO<MemberVacationDTO> getOneVacationList(PageRequestDTO pageRequestDTO, String eid) {
        // Pageable 객체 생성 (PageRequestDTO에서 페이지와 사이즈 정보 활용)
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("regTime").descending()
        );

        // 특정 eid를 가진 MemberVacation을 페이징 조회
        Page<MemberVacation> vacationPage = memberVacationRepository.findByMemberEid(pageable, eid);

        // 조회된 MemberVacation 목록을 DTO로 변환
        List<MemberVacationDTO> dtoList = vacationPage.getContent().stream()
                .map(vacation -> MemberVacationDTO.builder()
                        .vacId(vacation.getVacId())
                        .eid(vacation.getMember().getEid())
                        .memberName(vacation.getMember().getName())
                        .department(vacation.getMember().getDepartment())
                        .position(vacation.getMember().getPosition())
                        .vacType(vacation.getVacType())
                        .vacStatus(vacation.getVacStatus())
                        .vacStartDate(vacation.getVacStartDate())
                        .vacEndDate(vacation.getVacEndDate())
                        .vacDescription(vacation.getVacDescription())
                        .build())
                .collect(Collectors.toList());

        // PageResponseDTO를 생성하여 반환
        return PageResponseDTO.<MemberVacationDTO>withAll()
                .dtoList(dtoList)
                .totalCount(vacationPage.getTotalElements())
                .pageRequestDTO(pageRequestDTO)
                .build();
    }


}
