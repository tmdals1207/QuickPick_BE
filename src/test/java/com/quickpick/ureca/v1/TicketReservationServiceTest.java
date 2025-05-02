package com.quickpick.ureca.v1;

import com.quickpick.ureca.reserve.v1.service.ReserveServiceV1;
import com.quickpick.ureca.ticket.v1.domain.TicketV1;
import com.quickpick.ureca.ticket.v1.repository.TicketRepositoryV1;
import com.quickpick.ureca.user.v1.domain.UserV1;
import com.quickpick.ureca.user.v1.repository.UserRepositoryV1;
import com.quickpick.ureca.user.v1.service.UserBulkInsertService;
import com.quickpick.ureca.userticket.v1.domain.UserTicketV1;
import com.quickpick.ureca.userticket.v1.repository.UserTicketRepositoryV1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TicketReservationServiceTest {

    @Autowired private TicketRepositoryV1 ticketRepositoryV1;
    @Autowired private UserRepositoryV1 userRepositoryV1;
    @Autowired private UserTicketRepositoryV1 userTicketRepositoryV1;
    @Autowired private ReserveServiceV1 reserveServiceV1;

    @Autowired
    private UserBulkInsertService userBulkInsertService;

    @Test
    @DisplayName("동시에 1000개의 요청으로 100개의 티켓을 예약한다.")
    void PessimisticReservationTest() throws InterruptedException {
        int userCount = 30000;
        int ticketQuantity = 100;

        TicketV1 ticket = new TicketV1("SKT 콘서트", ticketQuantity);
        ticketRepositoryV1.save(ticket);

        userBulkInsertService.insertUsersInBulk(userCount);

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(userCount);

        List<UserV1> allUsers = userRepositoryV1.findAll();

        for (UserV1 user : allUsers) {
            executorService.submit(() -> {
                try {
                    reserveServiceV1.reserveTicket(user.getUserId(), ticket.getTicketId());
                } catch (Exception ignored) {
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        List<UserTicketV1> reservations = userTicketRepositoryV1.findAll();
        System.out.println("총 예약 수: " + reservations.size());
        assertEquals(ticketQuantity, reservations.size());
    }
}
