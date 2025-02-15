package com.example.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groups")
public class Group {
    
    @Id
    private String id;
    private String subject;
    private String timeSlot; // e.g., "monday-14"
    private List<String> userIds; // Store only user IDs

    public Group() {}

    public Group(String subject, String timeSlot, List<String> userIds) {
        this.subject = subject;
        this.timeSlot = timeSlot;
        this.userIds = userIds;
    }

    public String getId() { return id; }
    public String getSubject() { return subject; }
    public String getTimeSlot() { return timeSlot; }
    public List<String> getUserIds() { return userIds; }

    public void setSubject(String subject) { this.subject = subject; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }
    public void setUserIds(List<String> userIds) { this.userIds = userIds; }
}
