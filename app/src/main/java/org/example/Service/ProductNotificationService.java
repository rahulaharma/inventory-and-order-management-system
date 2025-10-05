package org.example.Service;

import org.springframework.transaction.annotation.Transactional;
import org.example.model.Product;
import org.example.model.User;
import org.example.repository.UserRepo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductNotificationService {
    private final UserRepo userRepo;
    private final NotificationService notificationService;

    public ProductNotificationService(UserRepo userRepo, NotificationService notificationService) {
        this.userRepo=userRepo;
        this.notificationService=notificationService;
    }
    @Async
    @Transactional
    public void sendNewProductNotification(Product savedProduct) {
        try {
            List<User> warehouseStaff=userRepo.findByRole_Name("WAREHOUSESTAFF");
            String message="New product added: '" + savedProduct.getName() + "'.Please update the inventory.";

            for (User staffMember : warehouseStaff) {
                notificationService.createNotification(staffMember, message);
            }
        }
        catch(Exception e){
            System.err.println("Failed to send notification for new product in async service: "+e.getMessage());
        }
    }
}
