package com.inhatc.empower.security;

import com.inhatc.empower.domain.Member;
import com.inhatc.empower.dto.MemberDTO;
import com.inhatc.empower.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
//memberDTO에 해당
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("----------------loadUserByUsername----------------------"+ username);
        Member member = memberRepository.getWithRoles(username);

        if(member == null) {
            throw new UsernameNotFoundException(username + "Not Found");
        }

        MemberDTO memberDTO = new MemberDTO(
                member.getEid(),
                member.getEmail(),          // 이메일
                member.getPw(),             // 비밀번호
                member.getName(),           // 이름
                member.getDepartment(),     // 부서
                member.getPhone(),          // 전화번호
                member.getAddress(),        // 주소
                member.getPosition(),       // 직급
                member.getHireDate(),       // 입사일
                member.isMemberCheck(),     // 가입 여부
                member.getMemberRoleList().stream()
                        .map(Enum::name).collect(Collectors.toList())
        );
        log.info(memberDTO);

        return memberDTO;
    }
}
