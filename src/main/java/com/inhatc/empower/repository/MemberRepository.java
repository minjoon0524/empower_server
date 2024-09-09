package com.inhatc.empower.repository;

import com.inhatc.empower.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member,String> {

    //@EntityGraph는 Role도 함께 가져오기 위해 사용
    @EntityGraph(attributePaths = {"memberRoleList"})
    @Query("select m from Member m where m.eid = :eid")
    Member getWithRoles(@Param("eid") String eid);

}
