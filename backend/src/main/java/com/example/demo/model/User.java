package com.example.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    
    @Id
    private String id;
    private String name;
    private String email;
    private List<Subject> subjects; 
    private List<String> availability; 

    public User() {}

    
    public User(String name, String email, List<Subject> subjects, List<String> availability) {
        this.name = name;
        this.email = email;
        this.subjects = subjects;
        this.availability = availability;
    }

    public String getId() {return id;}
    public void setId(String id) { this.id =id;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Subject> getSubjects() { return subjects; }
    public void setSubjects(List<Subject> subjects) { this.subjects = subjects; }

    public List<String> getAvailability() { return availability; }
    public void setAvailability(List<String> availability) { this.availability = availability; }
}