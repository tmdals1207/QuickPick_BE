package com.quickpick.ureca.userticket.v1.domain;

import com.quickpick.ureca.ticket.v1.domain.Ticket;
import com.quickpick.ureca.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ticket_id")
    private Long userTicketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public UserTicket(User user, Ticket ticket) {
        this.user = user;
        this.ticket = ticket;
    }
}