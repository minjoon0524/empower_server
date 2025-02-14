package com.inhatc.empower.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inhatc.empower.common.entity.BaseEntity;
import com.inhatc.empower.constant.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "memberRoleList")
public class Member extends BaseEntity {
    @Id
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
    private String profileName;



    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberVacation> vacationList = new ArrayList<>();


    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberRole> memberRoleList=new ArrayList<>();

    // 회원 권한을 추가 할 때 사용
    public void addRole(MemberRole memberRole){
        memberRoleList.add(memberRole);
    }

    // 회원 권한을 제거 할 때 사용
    public void clearRole(){
        memberRoleList.clear();
    }

    public void changeHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public void changePosition(String position) {
        this.position = position;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changePhone(String phone) {
        this.phone = phone;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeDepartment(String department) {
        this.department = department;
    }

    public void changePw(String pw) {
        this.pw = pw;
    }

    public void changeName(String name) {
        this.name = name;
    }

}
