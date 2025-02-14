package com.example.demo.reposirtory;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findBySubjectsContaining(String subject); 
}