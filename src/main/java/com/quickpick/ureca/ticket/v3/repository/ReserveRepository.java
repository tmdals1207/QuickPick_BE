package com.quickpick.ureca.ticket.v3.repository;

import com.quickpick.ureca.reserve.v3.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
}
