package org.example.Service;

import org.example.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(long id);
    void deleteUser(long id);

}
