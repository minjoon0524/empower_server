package com.inhatc.empower.repository;


import com.inhatc.empower.domain.Member;
import com.inhatc.empower.constant.MemberRole;
import com.inhatc.empower.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Log4j2
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testOneInsertMember(){
        log.info("----------testOneInsertMember Test----------");
//        Member member = Member.builder()
//                .eid("ADMIN")
//                .department("관리팀")
//                .position("관리자")
//                .name("EMPLOYEE")//employee
//                .pw(passwordEncoder.encode("admin"))
//
//                .build();
//        member.addRole(MemberRole.ADMIN);
//
//
//        memberRepository.save(member);
        Member member = Member.builder()
                .eid("user")
                .department("개발팀")
                .position("사원")
                .name("유민준")//employee
                .pw(passwordEncoder.encode("1111"))

                .build();
        member.addRole(MemberRole.USER);


        memberRepository.save(member);
    }

    @Test
    public void testInsertMember(){
        log.info("----------memberInsert Test----------");


        
        for(int i =1; i<50; i++) {
            Member member= Member.builder()
                    .eid("user"+i+"@aaa.com")
                    .email("user"+i+"@aaa.com")
                    .position("사원")
                    .phone("010-1234-5678")
                    .department("개발팀")
                    .name("홍길동"+i)
                    .address("인천광역시")
                    
                    .pw(passwordEncoder.encode("1111"))
                    .build();
            member.addRole(MemberRole.USER);
            memberRepository.save(member);
        }

        for(int i =51; i<=80; i++) {
            Member member= Member.builder()
                    .eid("user"+i+"@aaa.com")
                    .email("user"+i+"@aaa.com")
                    .position("팀장")
                    .phone("010-1234-5678")
                    .department("개발 2팀")
                    .name("홍길동"+i)
                    .address("서울광역시")

                    .pw(passwordEncoder.encode("1111"))
                    .build();
            member.addRole(MemberRole.MANAGER);
            memberRepository.save(member);
        }

        for(int i =81; i<=100; i++) {
            Member member= Member.builder()
                    .eid("user"+i+"@aaa.com")
                    .email("user"+i+"@aaa.com")
                    .position("부장")
                    .phone("010-1234-5678")
                    .department("개발팀")
                    .name("홍길동"+i)
                    .address("서울광역시")

                    .pw(passwordEncoder.encode("1111"))
                    .build();
            member.addRole(MemberRole.MANAGER);
            memberRepository.save(member);
        }
        

    }

    @Test
    public void testRead(){
        log.info("----------memberRead Test----------");
        String eid="user5@aaa.com";
        Member member = memberRepository.getWithRoles(eid);
        System.out.println(member);
        log.info(member);
        log.info(member.getMemberRoleList());

    }

//수정 테스트
    @Test
    public void testModify() {
        String eid="202445081";
        Member member = memberRepository.getWithRoles(eid);

        member.changeHireDate(LocalDate.of(2024,10,7));

        memberRepository.save(member);
    }


    @Test
    @DisplayName("권한 부여 테스트")
    public void grantUser() {
        System.out.println("============ 권한 부여 테스트=============");
        String eid="user3@aaa.com";

        memberService.grantUser(eid);

    }



}
