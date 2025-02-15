package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subjects")
public class Subject {
    
    @Id
    private String id;
    private String name;
    // can add topics, description, teachers , semester offerings etc

    public Subject() {}

    public Subject(String name) {
        this.name = name;
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
