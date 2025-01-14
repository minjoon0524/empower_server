package com.inhatc.empower.repository;

import com.inhatc.empower.constant.MemberVacationStatus;
import com.inhatc.empower.domain.MemberVacation;
import com.inhatc.empower.domain.QMemberVacation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@AllArgsConstructor
public class VacationRepositoryImpl implements VacationRepository {
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


    @Override
    public void flush() {

    }

    @Override
    public <S extends MemberVacation> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends MemberVacation> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<MemberVacation> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MemberVacation getOne(Long aLong) {
        return null;
    }

    @Override
    public MemberVacation getById(Long aLong) {
        return null;
    }

    @Override
    public MemberVacation getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends MemberVacation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends MemberVacation> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends MemberVacation> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends MemberVacation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends MemberVacation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MemberVacation> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends MemberVacation, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends MemberVacation> S save(S entity) {
        return null;
    }

    @Override
    public <S extends MemberVacation> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<MemberVacation> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<MemberVacation> findAll() {
        return List.of();
    }

    @Override
    public List<MemberVacation> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(MemberVacation entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends MemberVacation> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<MemberVacation> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<MemberVacation> findAll(Pageable pageable) {
        return null;
    }


}
