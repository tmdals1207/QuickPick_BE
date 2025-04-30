package com.quickpick.ureca.ticket.repository;

import com.quickpick.ureca.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
