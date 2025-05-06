package com.quickpick.ureca.reserve.v1.repository;

import com.quickpick.ureca.reserve.v1.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReserveRepositoryV1 extends JpaRepository<Reserve, Long> {
}
