package com.quickpick.ureca.v3.ticket.repository;

import com.quickpick.ureca.v3.reserve.domain.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
}
