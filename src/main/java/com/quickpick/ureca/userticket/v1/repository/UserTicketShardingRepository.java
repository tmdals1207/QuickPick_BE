package com.quickpick.ureca.userticket.v1.repository;

import com.quickpick.ureca.userticket.v1.domain.UserTicket;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserTicketShardingRepository {

    private final EntityManager em;

    // Shard 선택
    private String getTableName(Long userId) {
        int shard = (int)(userId % 10);
        return "user_ticket_" + shard;
    }

    public boolean exists(Long userId, Long ticketId) {
        String tableName = getTableName(userId);
        String sql = "SELECT 1 FROM " + tableName +
                " WHERE user_id = :userId AND ticket_id = :ticketId LIMIT 1";

        List<?> result = em.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("ticketId", ticketId)
                .getResultList();

        return !result.isEmpty();
    }

    public void saveIgnoreDuplicate(UserTicket userTicket) {
        String tableName = getTableName(userTicket.getUser().getUserId());

        String sql = "INSERT IGNORE INTO " + tableName + " (user_id, ticket_id) " +
                "VALUES (:userId, :ticketId)";

        em.createNativeQuery(sql)
                .setParameter("userId", userTicket.getUser().getUserId())
                .setParameter("ticketId", userTicket.getTicket().getTicketId())
                .executeUpdate();
    }

    public void save(UserTicket userTicket) {
        String tableName = getTableName(userTicket.getUser().getUserId());
        String sql = "INSERT INTO " + tableName + " (user_id, ticket_id) VALUES (:userId, :ticketId)";

        em.createNativeQuery(sql)
                .setParameter("userId", userTicket.getUser().getUserId())
                .setParameter("ticketId", userTicket.getTicket().getTicketId())
                .executeUpdate();
    }

    public void delete(Long userId, Long ticketId) {
        String tableName = getTableName(userId);
        String sql = "DELETE FROM " + tableName + " WHERE user_id = :userId AND ticket_id = :ticketId";

        em.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("ticketId", ticketId)
                .executeUpdate();
    }

}
