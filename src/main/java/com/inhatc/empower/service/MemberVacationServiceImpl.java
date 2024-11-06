package com.inhatc.empower.service;

import com.inhatc.empower.constant.MemberVacationStatus;
import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberAttendance;
import com.inhatc.empower.domain.MemberVacation;
import com.inhatc.empower.dto.MemberAttendanceDTO;
import com.inhatc.empower.dto.MemberVacationDTO;
import com.inhatc.empower.dto.PageRequestDTO;
import com.inhatc.empower.dto.PageResponseDTO;
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


//    @Override
//    public List<Member> getMemberVacationsList(String memberId) {
//        if(memberId == null) {
//            Member member = memberRepository.findWithVacationListByEid(memberId);
//            return List.of(member);
//
//        }
//
//        return List.of();
//    }

    @Override
    public PageResponseDTO<MemberVacationDTO> getMemberVacationsList(PageRequestDTO pageRequestDTO, String eid) {
        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1
                , pageRequestDTO.getSize()
                , Sort.by("reg_time").descending());


        // 회원 정보와 해당 회원의 모든 휴가 신청 내역을 페이징 처리하여 조회
        Page<MemberVacation> vacationPage = memberVacationRepository.findByMemberEid(eid, pageable);

        // MemberVacation 엔티티를 MemberVacationDTO로 변환
        List<MemberVacationDTO> dtoList = vacationPage.getContent().stream()
                .map(vacation -> MemberVacationDTO.builder()
                        .eid(vacation.getMember().getEid())
                        .vacId(vacation.getVacId())
                        .vacType(vacation.getVacType())
                        .vacStatus(vacation.getVacStatus())
                        .vacStartDate(vacation.getVacStartDate())
                        .vacEndDate(vacation.getVacEndDate())
                        .vacDescription(vacation.getVacDescription())
                        .build())
                .collect(Collectors.toList());

        // 전체 휴가 신청 내역 수
        long totalCount = vacationPage.getTotalElements();

        // PageResponseDTO 반환
        return PageResponseDTO.<MemberVacationDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();

    }

    @Override
    public MemberVacationDTO detailsVacation(Long vacId) {
        // 휴가 조회
        MemberVacation memberVacation = memberVacationRepository.findById(vacId).orElseThrow();

        return  modelMapper.map(memberVacation, MemberVacationDTO.class);
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

    }

    @Override
    public void approveVacation(Long vacId, MemberVacationStatus status) {

    }
}
