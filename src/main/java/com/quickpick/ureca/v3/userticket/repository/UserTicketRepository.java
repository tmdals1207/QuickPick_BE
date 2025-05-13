package com.quickpick.ureca.v3.userticket.repository;

import com.quickpick.ureca.v3.userticket.domain.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

    boolean existsByUserId(Long userId);
}
