package com.inhatc.empower.repository;

import com.inhatc.empower.constant.LeaveType;
import com.inhatc.empower.constant.MemberVacationStatus;
import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberVacation;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class MemberVacationRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberVacationRepository memberVacationRepository;

    @Test
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




}
