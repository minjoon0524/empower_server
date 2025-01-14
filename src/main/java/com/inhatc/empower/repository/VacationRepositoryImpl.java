package com.inhatc.empower.repository;

import com.inhatc.empower.constant.MemberVacationStatus;
import com.inhatc.empower.domain.MemberVacation;
import com.inhatc.empower.domain.QMemberVacation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class VacationRepositoryImpl implements VacationRepository {
    //QueryDSL 사용를 위한 Impl
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<MemberVacation> findVacation(Pageable pageable, String status) {
        QMemberVacation vaca = QMemberVacation.memberVacation;
        List<MemberVacation> content = jpaQueryFactory.selectFrom(vaca)
                .where(vaca.vacStatus.eq(MemberVacationStatus.valueOf(status)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory.selectFrom(vaca)
                .where(vaca.vacStatus.eq(MemberVacationStatus.valueOf(status)))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);}



}
