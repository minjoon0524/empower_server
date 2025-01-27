package com.inhatc.empower.repository;

import com.inhatc.empower.domain.MemberVacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberVacationRepository extends JpaRepository<MemberVacation,Long> {

    @EntityGraph(attributePaths = {"member"})
    Page<MemberVacation> findAll(Pageable pageable);

    // JPQL 방식
    @Query("SELECT mv FROM MemberVacation mv JOIN mv.member m WHERE m.eid = :eid")
    Page<MemberVacation> findByMemberEid(Pageable pageable,@Param("eid") String eid);

}
