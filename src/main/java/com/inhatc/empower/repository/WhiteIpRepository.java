package com.inhatc.empower.repository;

import com.inhatc.empower.domain.WhiteIp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WhiteIpRepository extends CrudRepository<WhiteIp, Long> {
    Optional<WhiteIp> findByAccessIp(String ip);
}