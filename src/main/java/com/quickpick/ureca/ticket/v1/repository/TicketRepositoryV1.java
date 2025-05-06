package com.quickpick.ureca.ticket.v1.repository;

import com.quickpick.ureca.ticket.v1.domain.Ticket;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepositoryV1 extends JpaRepository<Ticket, Long> {

    // 비관적 락
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select t from Ticket t where t.ticketId = :ticketId""")
    Optional<Ticket> findByIdForUpdate(Long ticketId);

    // 비관적 락 (네이티브 쿼리)
    @Query(value = "SELECT * FROM ticket WHERE ticket_id = :ticketId FOR UPDATE", nativeQuery = true)
    Ticket findByIdForUpdateNative(@Param("ticketId") Long ticketId);


    // open-in-view + FetchJoin + DTO + 비관적 락
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    select t from Ticket t
    left join fetch t.userTickets ut
    left join fetch ut.user
    where t.ticketId = :ticketId
    """)
    Optional<Ticket> findByIdForUpdateWithUsers(Long ticketId);

}
