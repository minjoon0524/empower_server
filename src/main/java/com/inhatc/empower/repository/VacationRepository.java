package com.inhatc.empower.repository;

import com.inhatc.empower.domain.MemberVacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VacationRepository extends JpaRepository<MemberVacation,Long> {
    Page<MemberVacation> findVacation(Pageable pageable,String status);
    @Query("SELECT mv FROM MemberVacation mv JOIN mv.member m WHERE m.eid = :eid")
    Page<MemberVacation> findByMemberEid(Pageable pageable,@Param("eid") String eid);
}
