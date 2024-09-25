package com.inhatc.empower.repository;


import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.MemberRole;
import com.inhatc.empower.dto.MemberDTO;
import com.inhatc.empower.dto.MemberSearchDTO;
import com.inhatc.empower.dto.PageRequestDTO;
import com.inhatc.empower.dto.PageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                    .email("user"+i+"@aaa.com")
                    .position("팀장")
                    .name("유민준"+i)
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

    @Test
    public void testMemberRead() {
        // PageRequestDTO 객체 생성
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1) // 페이지 번호 설정
                .size(10) // 페이지 크기 설정
                .build();

        // Pageable 객체 생성 (페이지 번호가 0부터 시작하므로 -1)
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("eid").descending());

        // Member 리스트 조회
        Page<Object[]> result = memberRepository.getMemberList(pageable);

        // MemberDTO 리스트 생성
        List<MemberSearchDTO> dtoList = result.get().map(arr -> {
            Member member = (Member) arr[0];

            // MemberDTO 변환
            MemberSearchDTO memberSearchDTO = MemberSearchDTO.builder()
                    .eid(member.getEid())
                    .name(member.getName())
                    .build();
            return memberSearchDTO;
        }).collect(Collectors.toList());

        // 전체 회원 수
        long totalCount = result.getTotalElements();

        // PageResponseDTO 반환
        PageResponseDTO<MemberSearchDTO> pageResponseDTO = PageResponseDTO.<MemberSearchDTO>withAll()
                .dtoList(dtoList)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();

        // 테스트 결과 출력 또는 추가 검증 로직 작성
       log.info(pageResponseDTO);
    }


    }
