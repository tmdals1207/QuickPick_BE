package com.quickpick.ureca.userticket.v1.repository;

import com.quickpick.ureca.userticket.v1.domain.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
}
