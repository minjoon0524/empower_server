package com.inhatc.empower.repository;

import com.inhatc.empower.domain.MemberAttendance;
import com.inhatc.empower.domain.QMember;
import com.inhatc.empower.domain.QMemberAttendance;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class MemberAttendanceCustomRepositoryImpl implements MemberAttendanceCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<MemberAttendance> findMemberAttendanceByDateAndOptions(LocalDateTime start, LocalDateTime end,
                                                                       String name, String department, String position,
                                                                       String eid, Pageable pageable) {
        QMemberAttendance memberAttendance = QMemberAttendance.memberAttendance;
        QMember member = QMember.member;

        // 조건 빌더 초기화
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(memberAttendance.checkInTime.between(start, end));

        // 동적 조건 추가
        if (name != null && !name.isEmpty()) {
            condition.and(member.name.like("%" + name + "%"));
        }
        if (department != null && !department.isEmpty()) {
            condition.and(member.department.like("%" + department + "%"));
        }
        if (position != null && !position.isEmpty()) {
            condition.and(member.position.like("%" + position + "%"));
        }
        if (eid != null && !eid.isEmpty()) {
            condition.and(member.eid.like("%" + eid + "%"));
        }

        // 데이터 조회
        List<MemberAttendance> results = jpaQueryFactory
                .selectFrom(memberAttendance)
                .join(memberAttendance.member, member).fetchJoin()
                .where(condition)
                .orderBy(memberAttendance.checkInTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 조회
        long total = jpaQueryFactory
                .selectFrom(memberAttendance)
                .join(memberAttendance.member, member) // 동일한 조인 조건
                .where(condition)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }




}
