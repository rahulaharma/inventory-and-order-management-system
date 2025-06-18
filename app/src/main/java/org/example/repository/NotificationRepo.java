package org.example.repository;

import org.example.model.Notification;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Long> {
    List<Notification> findByRecipient(User recipient);
    List<Notification> findByRecipientAndReadFalse(User recipient);
}
