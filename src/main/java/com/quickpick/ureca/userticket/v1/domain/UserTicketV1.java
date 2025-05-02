package com.quickpick.ureca.userticket.v1.domain;

import com.quickpick.ureca.ticket.v1.domain.TicketV1;
import com.quickpick.ureca.user.v1.domain.UserV1;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class UserTicketV1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ticket_id")
    private Long userTicketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserV1 user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private TicketV1 ticket;

    public UserTicketV1(UserV1 user, TicketV1 ticket) {
        this.user = user;
        this.ticket = ticket;
    }
}