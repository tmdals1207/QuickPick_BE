package com.quickpick.ureca.ticket.v3.repository;

import com.quickpick.ureca.ticket.v3.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Modifying
    @Query(value = "UPDATE tickets SET quantity = quantity - :amount WHERE ticket_id = :ticketId", nativeQuery = true)
    int decreaseStock(@Param("ticketId") Long ticketId, @Param("amount") int amount);
}