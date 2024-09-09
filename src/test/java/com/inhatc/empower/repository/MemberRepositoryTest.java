package com.inhatc.empower.repository;


import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertMember(){
        log.info("----------memberInsert Test----------");
        for(int i =0; i<10; i++) {
            Member member= Member.builder()
                    .eid("user"+i+"@aaa.com")
                    .pw(passwordEncoder.encode("1111"))
                    .build();
            member.addRole(MemberRole.USER);
            if(i >=5)
                member.addRole(MemberRole.MANAGER);
            if(i >=8)
                member.addRole(MemberRole.ADMIN);
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
}
