package com.quickpick.ureca.reserve.v1.domain;

import com.quickpick.ureca.reserve.status.ReserveStatus;
import com.quickpick.ureca.user.v1.domain.UserV1;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
@Entity
@Getter
@NoArgsConstructor
public class ReserveV1{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id")
    private Long reserveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserV1 user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReserveStatus status;
}
