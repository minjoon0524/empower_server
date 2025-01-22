package com.inhatc.empower.controller;

import com.inhatc.empower.constant.MemberRole;
import com.inhatc.empower.dto.*;
import com.inhatc.empower.service.MemberService;
import com.inhatc.empower.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final CustomFileUtil customFileUtil;


    // 사용자 조회(필터 및 검색어)
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')") //임시로 권한 설정
    @GetMapping("/list")
    public PageResponseDTO<MemberSearchDTO> list(
            PageRequestDTO pageRequestDTO,
            @RequestParam(name = "option") String option,
            @RequestParam(name = "term") String term) {
        log.info("list..........." + pageRequestDTO);
        return memberService.getMemberList(pageRequestDTO, option, term);
    }

    // 사용자 수정
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
    // 프로필 이미지 가져오기     
    @GetMapping("/profile/{fileName}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable("fileName") String fileName) {
        return customFileUtil.getFile(fileName);
    }
    // 회원 등록
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody MemberAddDTO memberAddDTO) {
        log.info("MemberDTO: " + memberAddDTO);
        String eid = memberService.register(memberAddDTO);
        return Map.of("EID", eid);
    }
    // 권한 부여를 위한 함수
    @PostMapping("/grant/{eid}")
    public Map<String, List<MemberRole>> grant(@PathVariable(name="eid") String eid) {
        List<MemberRole> memberRoles = memberService.grantUser(eid);
        return Map.of("EID", memberRoles);
    }

    // 회원 삭제
    @DeleteMapping("/{eid}")
    public Map<String, String> remove(@PathVariable(name="eid") String eid) {
        log.info("Remove: " + eid);
        memberService.remove(eid);
        return Map.of("RESULT", "SUCCESS");
    }

    // 로그인 한 사용자 및 권한 확인용
    @GetMapping("/loginOk")
    public ResponseEntity<Map<String, String>> loginOk() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        String authorities = authentication.getAuthorities().toString();

        System.out.println("로그인한 유저 이메일:" + email);
        System.out.println("유저 권한:" + authentication.getAuthorities());

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("email", email);
        userInfo.put("authorities", authorities);

        return ResponseEntity.ok(userInfo);
    }


}
