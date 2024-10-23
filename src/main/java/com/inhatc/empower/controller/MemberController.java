package com.inhatc.empower.controller;

import com.inhatc.empower.dto.*;
import com.inhatc.empower.service.MemberService;
import com.inhatc.empower.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final CustomFileUtil customFileUtil;

    // 사용자 조회를 위한 기능
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')") //임시로 권한 설정
    @GetMapping("/list")
    public PageResponseDTO<MemberSearchDTO> list(
            PageRequestDTO pageRequestDTO,
            @RequestParam(name = "option") String option,
            @RequestParam(name = "term") String term) {
        log.info("list..........." + pageRequestDTO);
        return memberService.getMemberList(pageRequestDTO, option, term);
    }
//    @PutMapping("/modify")
//    public Map<String,String> modify(@RequestBody MemberModifyDTO memberModifyDTO){
//        log.info("member modify: " + memberModifyDTO);
//
//        memberService.modify(memberModifyDTO);
//
//        return Map.of("result","modified");
//    }

    @PutMapping("/modify/{eid}")
    public Map<String, String> modify(
            @PathVariable(name = "eid") String eid,
            @RequestPart(value = "memberModifyDTO") MemberModifyDTO memberModifyDTO,
            @RequestPart(value = "profileName", required = false) MultipartFile profileName) {

        log.info("member modify: " + memberModifyDTO);

        memberService.modify(memberModifyDTO, profileName); // 수정 메서드 호출

        return Map.of("result", "modified");
    }



    @GetMapping("/{eid}")
    public MemberProfileDTO get(@PathVariable(name="eid") String eid) {
        log.info("Fetching member with eid: {}", eid);
        return memberService.get(eid);
    }

    @GetMapping("/profile/{fileName}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable("fileName") String fileName) {
        return customFileUtil.getFile(fileName);
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody MemberAddDTO memberAddDTO) {
        log.info("MemberDTO: " + memberAddDTO);
        String eid = memberService.register(memberAddDTO);
        return Map.of("EID", eid);
    }
    @DeleteMapping("/{eid}")
    public Map<String, String> remove(@PathVariable(name="eid") String eid) {
        log.info("Remove: " + eid);
        memberService.remove(eid);
        return Map.of("RESULT", "SUCCESS");
    }


}
