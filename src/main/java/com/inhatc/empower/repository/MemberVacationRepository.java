package com.inhatc.empower.repository;

import com.inhatc.empower.domain.MemberVacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberVacationRepository extends JpaRepository<MemberVacation,Long> {
    Page<MemberVacation> findByMemberEid(String eid, Pageable pageable);
}
