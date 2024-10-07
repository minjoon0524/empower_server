package com.inhatc.empower.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString

public class MemberDTO extends User {
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


    public MemberDTO(String eid, String email, String pw,String name,
                     String department, String phone, String address, String position,
                     LocalDate hireDate, boolean memberCheck, List<String> roleNames) {
        super(eid,pw, roleNames.stream().map(str->
                new SimpleGrantedAuthority("ROLE_"+str)).collect(Collectors.toList()));
        this.eid = eid;
        this.pw = pw;
        this.name = name;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.position = position;
        this.hireDate = hireDate;
        this.memberCheck = memberCheck;
        this.roleNames = roleNames;
    }


    public MemberDTO() {
        super("", "", new ArrayList<>()); // 기본 값으로 빈 문자열과 빈 리스트를 전달
    }
    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap=new HashMap<>();
        dataMap.put("eid", eid);
        dataMap.put("pw",pw);
        dataMap.put("name", name);
        dataMap.put("department", department);
        dataMap.put("email", email);
        dataMap.put("phone", phone);
        dataMap.put("address", address);
        dataMap.put("position", position);
        dataMap.put("hireDate", hireDate);
        dataMap.put("memberCheck", memberCheck);
        dataMap.put("roleNames", roleNames);
        System.out.println("======== dataMap 확인"+ dataMap);
        return dataMap;
    }

}
