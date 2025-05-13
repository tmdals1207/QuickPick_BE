package com.quickpick.ureca.v1.reserve.dto;

import com.quickpick.ureca.v1.ticket.domain.Ticket;
import com.quickpick.ureca.v1.user.domain.User;

public record TicketReserveResponseV1(
        Long ticketId,
        String ticketName,
        int remainingQuantity,
        String reservedByUsername
) {
    public static TicketReserveResponseV1 of(Ticket ticket, User user) {
        return new TicketReserveResponseV1(
                ticket.getTicketId(),
                ticket.getName(),
                ticket.getQuantity(),
                user.getId()
        );
    }
}
