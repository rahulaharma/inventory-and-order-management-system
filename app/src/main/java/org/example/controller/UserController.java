package org.example.controller;

import org.example.Service.UserService;
import org.example.model.LoginResponse;
import org.example.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
        System.out.println("-----------------------------USER IS REGISTEING!!!!!!!!!!!!!!!!!!!!-------------------------------------------");

        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody User user){
        System.out.println("-----------------------------USER LOGING!!!!!!!!!!!!!!!!!!!!-------------------------------------------");

        String token=userService.verifyUser(user);
        return ResponseEntity.ok(new LoginResponse(token));

    }



    /*@PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User saved=userService.registerUser(user);
        return ResponseEntity.status(201).body(saved);
    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id){
        return userService.getUserById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }*/
}
