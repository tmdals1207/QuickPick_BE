package com.quickpick.ureca.reserve.v2.repository;

import com.quickpick.ureca.reserve.v2.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepositoryV2 extends JpaRepository<Reserve, Long> {
}
