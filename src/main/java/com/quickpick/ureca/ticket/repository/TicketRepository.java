package com.quickpick.ureca.ticket.repository;

import com.quickpick.ureca.ticket.v1.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
