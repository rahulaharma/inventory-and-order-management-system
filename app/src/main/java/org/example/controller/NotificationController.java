package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.Service.NotificationService;
import org.example.Service.UserService;
import org.example.model.Notification;
import org.example.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final UserService userService;
    public NotificationController(NotificationService notificationService,UserService userService){
        this.notificationService=notificationService;
        this.userService=userService;
    }
    @PostMapping("/create")
    public Notification createNotification(@RequestBody Notification notification){
        return notificationService.createNotification(notification.getRecipient(),notification.getMessage());
    }
    @PutMapping("/mark-as-read/{id}")
    public void markAsRead(@PathVariable long id){
        notificationService.markAsRead(id);
    }
    @GetMapping("/user/{userId}")
    public List<Notification> getNotifications(@PathVariable long userId){
        User user=userService.getUserById(userId)
                .orElseThrow(()->new EntityNotFoundException("User not found with id:"+userId));
        return notificationService.getNotificationsForUser(user);
    }
    @GetMapping("/user/{userId}/unread")
    public List<Notification> getUnreadNotification(@PathVariable long userId){
        User user=userService.getUserById(userId)
                .orElseThrow(()->new EntityNotFoundException("User not found with id:"+userId));
        return notificationService.getUnreadNotifications(user);
    }

}
