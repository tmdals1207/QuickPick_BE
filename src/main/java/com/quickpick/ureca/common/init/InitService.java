package com.quickpick.ureca.common.init;

import com.quickpick.ureca.ticket.v1.domain.Ticket;
import com.quickpick.ureca.ticket.v1.repository.TicketRepositoryV1;
import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class InitService {

    private final TicketRepositoryV1 ticketRepository;
    private final UserRepository userRepository;

    public String initialize(int ticketCount, int userCount, LocalDateTime startDate, LocalDateTime reserveDate) {
        ticketRepository.deleteAll();
        userRepository.deleteAll();

        Ticket ticket = Ticket.builder()
                .name("테스트 티켓")
                .quantity(ticketCount)
                .startDate(startDate != null ? startDate : LocalDateTime.now().plusDays(1))
                .reserveDate(reserveDate != null ? reserveDate : LocalDateTime.now())
                .build();
        ticketRepository.save(ticket);

        List<User> users = new ArrayList<>();
        for (int i = 1; i <= userCount; i++) {
            User user = User.builder()
                    .id("user" + i)
                    .password("pw" + i)
                    .name("User" + i)
                    .age("20")
                    .gender("M")
                    .build();
            users.add(user);
        }
        userRepository.saveAll(users);

        return "초기화 완료: 티켓 1개(" + ticketCount + "개 수량), 유저 " + userCount + "명 생성";
    }
}
