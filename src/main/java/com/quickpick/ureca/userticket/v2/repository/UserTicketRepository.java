package com.quickpick.ureca.userticket.v2.repository;

import com.quickpick.ureca.ticket.v2.domain.Ticket;
import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.userticket.v2.domain.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {
    boolean existsByUserAndTicket(User user, Ticket ticket);
    Optional<UserTicket> findByUserAndTicket(User user, Ticket ticket);
}