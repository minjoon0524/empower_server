package com.inhatc.empower.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberModifyDTO {
    private String eid; // 사번
    private String name;
    private String pw;
    private String department; //부서
    private String email;
    private String phone;
    private String address;
    private String position;//직급

    private LocalDate hireDate; //입사일
    private String profileName; // 프로필 사진 파일 이름 추가


}
