package com.quickpick.ureca.ticket.v2.repository;

import com.quickpick.ureca.ticket.v2.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepositoryV2 extends JpaRepository<Ticket, Long> {
}
