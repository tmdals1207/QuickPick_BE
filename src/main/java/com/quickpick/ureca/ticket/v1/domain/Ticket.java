package com.quickpick.ureca.ticket.v1.domain;

import com.quickpick.ureca.common.domain.BaseEntity;
import com.quickpick.ureca.userticket.v1.domain.UserTicket;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime reserveDate;

    //@Version
    //private Long version;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTicket> userTickets = new ArrayList<>();

    // 재고 감소 메서드
    public void decreaseCount() {
        if (this.quantity > 0) {
            this.quantity--;
        } else {
            throw new RuntimeException("티켓이 매진되었습니다.");
        }
    }

    // Test용
    public Ticket(String name, int i) {
        this.name = name;
        this.quantity = i;
    }

}