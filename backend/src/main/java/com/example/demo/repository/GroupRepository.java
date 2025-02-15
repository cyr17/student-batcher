package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Group;

public interface GroupRepository extends MongoRepository<Group, String> {
}