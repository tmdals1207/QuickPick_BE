package com.quickpick.ureca.ticket.v2.service;

public interface TicketServiceV2 {
    void orderTicket(Long ticketId, Long userId);
    void cancelTicket(Long ticketId, Long userId);
}
