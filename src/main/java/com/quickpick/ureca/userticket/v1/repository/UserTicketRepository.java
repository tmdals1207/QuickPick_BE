package com.quickpick.ureca.userticket.v1.repository;

import com.quickpick.ureca.userticket.v1.domain.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

    boolean existsByUser_UserIdAndTicket_TicketId(Long userId, Long ticketId);

    @Query(value = "SELECT 1 FROM user_ticket WHERE user_id = :userId AND ticket_id = :ticketId LIMIT 1", nativeQuery = true)
    Integer existsUserTicketRaw(@Param("userId") Long userId, @Param("ticketId") Long ticketId);



}
