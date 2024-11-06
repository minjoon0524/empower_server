package com.inhatc.empower.controller;

import com.inhatc.empower.dto.MemberVacationDTO;
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

    // 휴가등록
    @PostMapping("/register")
    public Map<String,Long> registerVacation(@RequestBody MemberVacationDTO memberVacationDTO){
        log.info("============== 휴가 등록 ==============");
        Long vacId = memberVacationService.insertVacation(memberVacationDTO);
        return Map.of("result",vacId);
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
}
