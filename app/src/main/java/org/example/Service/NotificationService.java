package org.example.Service;

import org.example.model.User;
import org.springframework.stereotype.Service;
import org.example.model.Notification;

import java.util.List;
public interface NotificationService {
    public List<Notification> getNotificationsForUser(User user);
    public List<Notification> getUnreadNotifications(User user);
    public Notification createNotification(User user, String message);
    public void markAsRead(Long notificationId);

}
