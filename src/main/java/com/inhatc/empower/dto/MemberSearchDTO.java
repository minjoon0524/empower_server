package com.inhatc.empower.dto;

import com.inhatc.empower.domain.MemberRole;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSearchDTO {
    private String eid; // 사번
    private String name;
    private String pw;
    private String department; //부서
    private String email;
    private String phone;
    private String address;
    private String position;//직급
    private LocalDate hireDate; //입사일
    private boolean memberCheck; // 가입 여부

    @Builder.Default
    private List<String> roleNames = new ArrayList<>();


}
