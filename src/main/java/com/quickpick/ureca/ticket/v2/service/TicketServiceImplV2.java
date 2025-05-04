package com.quickpick.ureca.ticket.v2.service;

import com.quickpick.ureca.ticket.repository.TicketRepository;
import com.quickpick.ureca.ticket.v2.domain.Ticket;
import com.quickpick.ureca.ticket.v2.repository.TicketRepositoryV2;
import com.quickpick.ureca.user.domain.User;
import com.quickpick.ureca.user.repository.UserRepository;
import com.quickpick.ureca.userticket.v2.domain.UserTicket;
import com.quickpick.ureca.userticket.v2.repository.UserTicketRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImplV2 implements TicketServiceV2 {
    private final TicketRepositoryV2 ticketRepository;
    private final UserRepository userRepository;
    private final UserTicketRepository userTicketRepository;
    private final RedissonClient redissonClient;

    /**
     * TEST 2
     */
//    @Override
//    @Transactional
//    public void orderTicket(Long ticketId, Long userId) {
//        int retry = 3; // 재시도 횟수
//        while (retry-- > 0) {
//            try {
//                processOrder(ticketId, userId);
//                return; // 성공하면 종료
//            } catch (ObjectOptimisticLockingFailureException e) {
//                if (retry == 0) throw e;
//            }
//        }
//    }
//
//    private void processOrder(Long ticketId, Long userId) {
//        Ticket ticket = ticketRepository.findById(ticketId)
//                .orElseThrow(() -> new RuntimeException("존재하지 않는 티켓입니다."));
//
//        if (ticket.getQuantity() <= 0) {
//            throw new RuntimeException("매진된 티켓입니다.");
//        }
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
//
//        if (userTicketRepository.existsByUserAndTicket(user, ticket)) {
//            throw new RuntimeException("이미 예매한 티켓입니다.");
//        }
//
//        ticket.setQuantity(ticket.getQuantity() - 1);
//        ticketRepository.save(ticket); // save() 시 OptimisticLock 체크 발생
//
//        UserTicket userTicket = new UserTicket(user, ticket);
//        userTicketRepository.save(userTicket);
//    }


    /**
     * TEST3
     */
//    @Override
//    @Transactional
//    public void orderTicket(Long ticketId, Long userId) {
//        RLock lock = redissonClient.getLock("ticketLock:" + ticketId);
//
//        boolean isLocked = false;
//        try {
//            // 최대 2초 대기, 5초 안에 락 자동 해제
//            isLocked = lock.tryLock(2, 5, TimeUnit.SECONDS);
//
//            if (!isLocked) {
//                throw new RuntimeException("잠시 후 다시 시도해주세요.");
//            }
//
//            Ticket ticket = ticketRepository.findById(ticketId)
//                    .orElseThrow(() -> new RuntimeException("존재하지 않는 티켓입니다."));
//            if (ticket.getQuantity() <= 0) {
//                throw new RuntimeException("매진된 티켓입니다.");
//            }
//
//            User user = userRepository.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
//            if (userTicketRepository.existsByUserAndTicket(user, ticket)) {
//                throw new RuntimeException("이미 예매한 티켓입니다.");
//            }
//
//            ticket.setQuantity(ticket.getQuantity() - 1);
//            ticketRepository.save(ticket);
//
//            UserTicket userTicket = new UserTicket(user, ticket);
//            userTicketRepository.save(userTicket);
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException("락 획득 실패", e);
//        } finally {
//            if (isLocked) {
//                lock.unlock();
//            }
//        }
//    }

    /**
     * TEST4
     */
    @Override
    @Transactional
    public void orderTicket(Long ticketId, Long userId) {
        RLock lock = redissonClient.getLock("ticketLock:" + ticketId);
        boolean isLocked = false;

        try {
            // 최대 2초 대기 후 락 획득 시도, 락은 5초 후 자동 해제
            isLocked = lock.tryLock(2, 5, TimeUnit.SECONDS);
            if (!isLocked) {
                throw new RuntimeException("잠시 후 다시 시도해주세요.");
            }

            // 락 획득 후 티켓 조회 & 재고 확인
            Ticket ticket = ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 티켓입니다."));

            if (ticket.getQuantity() <= 0) {
                throw new RuntimeException("매진된 티켓입니다.");
            }

            // 티켓 수량 감소
            ticket.setQuantity(ticket.getQuantity() - 1);
            ticketRepository.save(ticket);

            // 유저는 락 외부에서 조회해도 안전 (변동 없음)
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

            if (userTicketRepository.existsByUserAndTicket(user, ticket)) {
                throw new RuntimeException("이미 예매한 티켓입니다.");
            }

            // UserTicket 저장 (락 밖으로 빼도 됨 - 부작용 없음)
            UserTicket userTicket = new UserTicket(user, ticket);
            userTicketRepository.save(userTicket);

        } catch (InterruptedException e) {
            throw new RuntimeException("락 획득 실패", e);
        } finally {
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }



    @Override
    public void cancelTicket(Long ticketId, Long userId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 티켓입니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        UserTicket userTicket = userTicketRepository.findByUserAndTicket(user, ticket)
                .orElseThrow(() -> new RuntimeException("예매 기록이 없습니다."));

        userTicketRepository.delete(userTicket);

        ticket.setQuantity(ticket.getQuantity() + 1);
        ticketRepository.save(ticket);
    }
}
