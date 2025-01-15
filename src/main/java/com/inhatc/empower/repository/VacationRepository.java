package com.inhatc.empower.repository;

import com.inhatc.empower.domain.MemberVacation;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface VacationRepository {
    Page<MemberVacation> findVacation(Pageable pageable, String status);
}
