package com.inhatc.empower.controller;

import com.inhatc.empower.dto.*;
import com.inhatc.empower.service.MemberVacationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/vacation")
public class MemberVacationController {

    // memberVacationService 주입
    private final MemberVacationService memberVacationService;


    // 휴가조회
    @GetMapping("/{vacId}")
    public MemberVacationDTO getMemberVacation(@PathVariable(name = "vacId") Long vacId) {
        log.info("============== 휴가 조회(휴가 번호) ==============");
        return memberVacationService.detailsVacation(vacId);
    }

    // 휴가 전체 리스트 조회(전회원, 관리자용)
    @GetMapping("/list")
    public PageResponseDTO<MemberVacationDTO> getMemberVacationList(PageRequestDTO pageRequestDTO){
        log.info("============== 휴가 전체 리스트 조회(전회원, 관리자용) ==============");
        log.info("list..........." + pageRequestDTO);
        return memberVacationService.getAllVacationList(pageRequestDTO);
    }

    // 로그인한 사용자 휴가리스트 조회
    @GetMapping("/member/{eid}")
    public PageResponseDTO<MemberVacationDTO> getVacation(@PathVariable(name = "eid") String eid,PageRequestDTO pageRequestDTO){
        log.info("============== 특정 회원 휴가 리스트 조회 ==============");
        log.info("list..........." + pageRequestDTO);
        return memberVacationService.getOneVacationList(pageRequestDTO,eid);
    }

    // 휴가등록
    @PostMapping("/register")
    public Map<String,String> registerVacation(@RequestBody MemberVacationModifyDTO memberVacationDTO){
        log.info("============== 휴가 등록 ==============");
        String s = memberVacationService.insertVacation(memberVacationDTO);
        return Map.of("result",s);
    }

    // 휴가 승인(관리자)
    @PostMapping("/approve")
    public Map<String,String> approveVacation(@RequestBody MemberVacationAttendanceDTO memberVacationAttendanceDTO){
        log.info("============== 휴가(승인 OR 거절) ==============");
        String result = memberVacationService.approveVacation(memberVacationAttendanceDTO);
        return Map.of("Approve Result : ",result);
    }

    // 휴가수정
    @PutMapping({"/{vacId}"})
    public Map<String,Long> updateVacation(@PathVariable(name = "vacId") Long vacId, @RequestBody MemberVacationDTO memberVacationDTO){
        log.info("============== 휴가 수정 ==============");
        memberVacationDTO.setVacId(vacId);
        log.info("Modify: " + memberVacationDTO);
        memberVacationService.modifyVacation(memberVacationDTO);
        return Map.of("Modify Result : ",vacId);
    }

    // 휴가삭제
    @DeleteMapping({"/{vacId}"})
    public Map<String,Long> deleteVacation(@PathVariable(name = "vacId") Long vacId){
        log.info("============== 휴가 삭제 ==============");
        memberVacationService.deleteVacation(vacId);
        return Map.of("Modify Result : ",vacId);
    }

    // 휴가 상태에 따라 리스트 출력
    @GetMapping("/status")
    public PageResponseDTO<MemberVacationDTO> getMemberVacationStatus(PageRequestDTO pageRequestDTO,@RequestParam(name = "status") String status){
        log.info("============== 휴가 상태에 따라 리스트 출력  ==============");
        return memberVacationService.getStatusVacationList(pageRequestDTO,status);
    }




}
