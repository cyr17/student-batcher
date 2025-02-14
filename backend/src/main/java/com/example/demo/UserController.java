package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api/users")

public class UserController {

    @GetMapping("/test")
    public String testEndpoint( ) {
        return "Study Groupp BAtcher Api is running!!";
    }

    @GetMapping("/create")
    public String createUser() {
        return "Creating User";
    }

    @GetMapping("/get")
    public String getUser() {
        return "Getting User";
    }
    
    @GetMapping("/update")
    public String updateUser() {
        return "Updating User";
    }
    

    @GetMapping("/delete")
    public String deleteUser() {
        return "Deleting User";
    }
    
    
}
