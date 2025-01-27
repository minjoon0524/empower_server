package com.inhatc.empower.repository;

import com.inhatc.empower.domain.Member;
import com.inhatc.empower.domain.QMember;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Member> findMember(Pageable pageable, String searchType, String keyword) {
        // 조건 빌더 초기화
        BooleanBuilder condition = new BooleanBuilder();
        QMember member = QMember.member;

        // 검색 조건 추가
        if (searchType != null && keyword != null && !keyword.trim().isEmpty()) {
            switch (searchType) {
                case "name":
                    condition.and(member.name.containsIgnoreCase(keyword)); // 이름 검색
                    break;
                case "department":
                    condition.and(member.department.containsIgnoreCase(keyword)); // 부서 검색
                    break;
                case "position":
                    condition.and(member.position.containsIgnoreCase(keyword)); // 직급 검색
                    break;
                case "eid":
                    condition.and(member.eid.containsIgnoreCase(keyword)); // 사번 검색
                    break;
                case "phone":
                    condition.and(member.phone.containsIgnoreCase(keyword)); // 전화번호 검색
                    break;
                default:
                    throw new IllegalArgumentException("잘못된 검색 유형입니다: " + searchType);
            }
        }

        // 데이터 조회
        List<Member> results = jpaQueryFactory
                .selectFrom(member)
                .where(condition)
                .orderBy(member.email.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 조회
        long total = jpaQueryFactory
                .selectFrom(member)
                .where(condition)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }


}

