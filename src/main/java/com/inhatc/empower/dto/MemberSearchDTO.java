package com.inhatc.empower.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inhatc.empower.constant.MemberAttendanceStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private boolean memberCheck; // 가입 여부

    @Builder.Default
    private List<String> roleNames = new ArrayList<>();


}
