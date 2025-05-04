package com.quickpick.ureca.ticket.v1.dto;

import com.quickpick.ureca.ticket.v1.domain.TicketV1;
import com.quickpick.ureca.user.v1.domain.UserV1;

public record TicketReserveResponse(
        Long ticketId,
        String ticketName,
        int remainingQuantity,
        String reservedByUsername
) {
    public static TicketReserveResponse of(TicketV1 ticket, UserV1 user) {
        return new TicketReserveResponse(
                ticket.getTicketId(),
                ticket.getName(),
                ticket.getQuantity(),
                user.getId()
        );
    }
}
