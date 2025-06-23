package org.example.Service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Notification;
import org.example.model.User;
import org.example.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImp implements NotificationService{

    @Autowired
    private NotificationRepo notificationRepo;

    @Override
    public List<Notification> getNotificationsForUser(User user) {
        return notificationRepo.findByRecipient(user);
    }

    @Override
    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepo.findByRecipientAndReadStatusFalse(user);
    }

    @Override
    public Notification createNotification(User user, String message) {
        Notification notification=new Notification();
        notification.setRecipient(user);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setReadStatus(false);
        return notificationRepo.save(notification);
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notification=notificationRepo.findById(notificationId)
                .orElseThrow(()->new EntityNotFoundException("Notification not found for id:"+notificationId));
        notification.setReadStatus(true);
        notificationRepo.save(notification);
    }
}
