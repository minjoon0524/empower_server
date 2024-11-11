package com.inhatc.empower.repository;

import com.inhatc.empower.constant.LeaveType;
import com.inhatc.empower.constant.MemberVacationStatus;
import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberVacation;
import com.inhatc.empower.dto.MemberVacationDTO;
import com.inhatc.empower.service.MemberVacationService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class MemberVacationRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberVacationRepository memberVacationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MemberVacationService memberVacationService;

    @Test
    @DisplayName("휴가 신청테스트")
    public void testOneInsertVacation() {
        log.info("----------testOneInsertVacation Test----------");
        Member member = Member.builder()
                .eid("2024123")
                .email("2024123@aaa.com")
                .address("인천광역시")
                .department("관리팀")
                .position("관리자")
                .name("관리자")
                .pw(passwordEncoder.encode("1111"))
                .build();
        memberRepository.save(member);

        MemberVacation memberVacation=MemberVacation.builder()
                .member(member)
                .vacStartDate(LocalDate.of(2024, 11, 5))
                .vacEndDate(LocalDate.of(2024, 11, 6))
                .vacDescription("가족여행")
                .vacType(LeaveType.GENERAL)
                .vacStatus(MemberVacationStatus.PENDING)
                .build();
        memberVacationRepository.save(memberVacation);
        memberRepository.save(member);
    }

//    @Test
//    public void insertVacation(){
//        log.info("----------testInsertVacation Test----------");
//
//        Member member = memberRepository.getWithRoles("user1@aaa.com");
//
//        MemberVacationDTO memberVacationDTO =MemberVacationDTO.builder()
//                .eid(member.getEid())
//                .vacStartDate(LocalDate.of(2024, 11, 5))
//                .vacEndDate(LocalDate.of(2024, 11, 6))
//                .vacDescription("가족여행")
//                .vacType(LeaveType.GENERAL)
//                .vacStatus(MemberVacationStatus.PENDING)
//                .build();
//
//        Long vacId = memberVacationService.insertVacation(memberVacationDTO);
//        System.out.println(vacId);
//
//
//    }

//    @Test
//    @Transactional
//    @DisplayName("휴가리스트 불러오기 테스트")
//    public void getMemberVacationsList(){
//        log.info("----------testGetMemberVacationsList Test----------");
//        String eid="user1@aaa.com";
//        Member member = memberRepository.findWithVacationListByEid(eid);
//        System.out.println(member);
//        System.out.println(List.of(member));
//
//    }


    @Test
    @DisplayName("휴가 수정테스트")
    public void modifyVacation() {
        log.info("----------testModifyVacation Test----------");

        Long vacId = 3L; // 수정할 휴가 ID
        MemberVacationDTO memberVacationDTO = MemberVacationDTO.builder()
                .vacStartDate(LocalDate.of(2024, 11, 5))
                .vacEndDate(LocalDate.of(2024, 11, 10))
                .vacDescription("부조")
                .vacType(LeaveType.GENERAL)
                .vacStatus(MemberVacationStatus.PENDING)
                .build();

        Optional<MemberVacation> result = memberVacationRepository.findById(vacId);
        if(result.isPresent()){
            MemberVacation memberVacation1 = result.get();
            memberVacation1.setVacDescription("부조조조");
            memberVacation1.setVacEndDate(LocalDate.of(2024, 11, 20));
            memberVacation1.setVacStatus(MemberVacationStatus.REJECT);

            memberVacationRepository.save(memberVacation1);


        }

    }

    @Test
    @DisplayName("휴가 상세조회 테스트")
    public void detailsVacation(){
        log.info("----------testDetailsVacation Test----------");
        Long vacId = 2L;
        MemberVacationDTO memberVacationDTO = memberVacationService.detailsVacation(vacId);
        System.out.println("===============");
        System.out.println(memberVacationDTO);


    }

    @Test
    @DisplayName("휴가 삭제 테스트")
    public void deleteVacation(){
        log.info("----------testDeleteVacation Test----------");
        Long vacId = 1L;
        memberVacationRepository.deleteById(vacId);
    }

    @Test
    @DisplayName("휴가 리스트 테스트")
    public void testListVacation(){
        log.info("----------testListVacation Test----------");

        Pageable pageable = PageRequest.of(0,10, Sort.by("vacId").descending());

        Page<MemberVacation> result = memberVacationRepository.findAll(pageable);

        log.info(result.getTotalElements());

        result.getContent().stream().forEach(todo -> log.info(todo));
    }



}