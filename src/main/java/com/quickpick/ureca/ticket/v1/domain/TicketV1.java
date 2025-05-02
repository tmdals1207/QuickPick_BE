package com.quickpick.ureca.ticket.v1.domain;

import com.quickpick.ureca.userticket.v1.domain.UserTicketV1;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticketv1")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketV1{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Version
    private Long version;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTicketV1> userTickets = new ArrayList<>();

    // 재고 감소 메서드
    public void decreaseCount() {
        if (this.quantity > 0) {
            this.quantity--;
        } else {
            throw new RuntimeException("티켓이 매진되었습니다.");
        }
    }

    public TicketV1(String skt_콘서트, int i) {
        this.name = skt_콘서트;
        this.quantity = i;
    }

}