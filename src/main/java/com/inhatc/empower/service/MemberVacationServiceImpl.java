package com.inhatc.empower.service;

import com.inhatc.empower.constant.MemberVacationStatus;
import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberVacation;
import com.inhatc.empower.dto.*;
import com.inhatc.empower.repository.MemberRepository;
import com.inhatc.empower.repository.MemberVacationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberVacationServiceImpl implements MemberVacationService {

    private final MemberRepository memberRepository;
    private final MemberVacationRepository memberVacationRepository;
    private final ModelMapper modelMapper;
    private final JavaMailSender mailSender;

    @Override
    public String insertVacation(MemberVacationModifyDTO memberVacationModifyDTO) {

        // 임시 관리자 이메일
        String email="minjoon0524@gmail.com";

        // 회원정보 가져오기
        Member member = memberRepository.getWithRoles(memberVacationModifyDTO.getEid());
        // DTO를  Entity로 변환
        MemberVacation memberVacation = modelMapper.map(memberVacationModifyDTO, MemberVacation.class);
        memberVacation.setMember(member);
        log.info(memberVacation);
        memberVacationRepository.save(memberVacation);
        sendEmail(email, "[EMPOWER] 휴가 신청", memberVacation.getMember().getName()+"님이 휴가 신청하셨습니다.");

        return memberVacationModifyDTO.getEid();
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
                        .regTime(vacation.getRegTime())
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
        } else {
            throw new EntityNotFoundException("휴가 : " + memberVacationDTO.getVacId() + " 를 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteVacation(Long vacId) {
        log.info("==== 휴가 삭제 ====");
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
            // 이메일 전송 로직 추가
            memberVacation.getMember().getName();
            sendEmail(memberVacation.getMember().getEmail(), "[EMPOWER] 휴가 승인", memberVacation.getMember().getName()+"님의 휴가가 승인되었습니다.");
        } else if (memberVacationAttendanceDTO.getVacStatus().equals(MemberVacationStatus.REJECT)) {
            // 거절 처리
            result = "거절 처리 완료";
            sendEmail(memberVacation.getMember().getEmail(), "[EMPOWER] 휴가 거절", memberVacation.getMember().getName()+"님의 휴가가 거절되었습니다.");
        } else {
            // 다른 상태 (예: 대기)
            result = "상태 변경 완료";
        }

        // DB 저장
        memberVacationRepository.save(memberVacation);

        return result;
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public PageResponseDTO<MemberVacationDTO> getOneVacationList(PageRequestDTO pageRequestDTO, String eid) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("regTime").descending()
        );

        Page<MemberVacation> vacationPage = memberVacationRepository.findByMemberEid(pageable, eid);

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
                        .regTime(vacation.getRegTime())
                        .build())
                .collect(Collectors.toList());

        return PageResponseDTO.<MemberVacationDTO>withAll()
                .dtoList(dtoList)
                .totalCount(vacationPage.getTotalElements())
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
