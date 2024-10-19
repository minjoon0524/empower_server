package com.inhatc.empower.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddDTO {
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

    private List<String> roleNames = new ArrayList<>();
}
