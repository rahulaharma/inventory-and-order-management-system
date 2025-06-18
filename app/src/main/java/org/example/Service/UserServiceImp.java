package org.example.Service;

import org.example.model.User;
import org.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Override
    public User registerUser(User user) {
        return userRepo.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    @Override
    public Optional<User> getUserById(long id) {
        return userRepo.findById(id);
    }
    @Override
    public void deleteUser(long id) {
        userRepo.deleteById(id);
    }
}
