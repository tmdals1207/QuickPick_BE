package com.quickpick.ureca.reserve.dto;

import com.quickpick.ureca.ticket.domain.Ticket;
import com.quickpick.ureca.user.domain.User;

public record TicketReserveResponse(
        Long ticketId,
        String ticketName,
        int remainingQuantity,
        String reservedByUsername
) {
    public static TicketReserveResponse of(Ticket ticket, User user) {
        return new TicketReserveResponse(
                ticket.getTicketId(),
                ticket.getName(),
                ticket.getQuantity(),
                user.getId()
        );
    }
}
