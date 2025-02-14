package com.example.demo.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;



@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    // CRUD OPERATIONS
    @GetMapping("/test")
    public String testEndpoint( ) {
        return "Study Groupp BAtcher Api is running!!";
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable String id) {
        return userRepository.findById(id);
    }
    
    @GetMapping("/update")
    public String updateUser() {
        return "Updating User";
    }
    

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id){
        userRepository.deleteById(id);
        return "User deleted Successfully!";
    }
}
