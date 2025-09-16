package org.example.Service;

import org.example.model.Role;
import org.example.model.User;
import org.example.repository.RoleRepo;
import org.example.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImp implements UserService{

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepo roleRepo;

    public UserServiceImp(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.roleRepo = roleRepo;
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String roleName=user.getRole().getName();
        Role userRole=roleRepo.findByName(roleName).
                orElseThrow(()->new RuntimeException("Error:Role not found"));
        user.setRole(userRole);
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
    public String verifyUser(User user){
        System.out.println(user.getUsername());
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()){
            User authenticatedUser = userRepo.findByUsername(user.getUsername())
                    .orElseThrow(()->new RuntimeException("Authenticated user not found"));
            System.out.println(authenticatedUser.getRole().getName());
            return jwtService.generateToken(authenticatedUser);
        }
        return "login failure";
    }
}
