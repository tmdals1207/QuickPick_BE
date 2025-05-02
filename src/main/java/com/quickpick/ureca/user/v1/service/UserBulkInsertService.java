package com.quickpick.ureca.user.v1.service;

import com.quickpick.ureca.user.v1.domain.UserV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBulkInsertService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertUsersInBulk(int userCount) {
        List<UserV1> users = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            users.add(new UserV1("user" + i));
        }

        int batchSize = 100;
        for (int i = 0; i < users.size(); i++) {
            entityManager.persist(users.get(i));
            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}
