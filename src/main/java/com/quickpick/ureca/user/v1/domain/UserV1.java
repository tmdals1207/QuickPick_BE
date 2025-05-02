package com.quickpick.ureca.user.v1.domain;

import com.quickpick.ureca.userticket.v1.domain.UserTicketV1;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserV1{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTicketV1> userTickets = new ArrayList<>();

    public UserV1(String id) {
        this.id = id;
    }

}
