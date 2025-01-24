package com.inhatc.empower.repository;

import com.inhatc.empower.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberCustomRepository {
    // 옵션 선택 후 검색
    Page<Member> findMember(Pageable pageable, String searchType, String keyword);
}
