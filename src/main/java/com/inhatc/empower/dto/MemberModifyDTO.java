package com.inhatc.empower.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberModifyDTO {
    private String eid; // 사번
    private String name;
    private String pw;
    private String department; //부서
    private String email;
    private String phone;
    private String address;
    private String position;//직급

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate; //입사일
    private boolean memberCheck; // 가입 여부


}
