package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Subject;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class GroupService {
    
    @Autowired
    private UserRepository userRepository;

    public List<List<User>> matchUsersBySubjectAndAvailability() {
        // Get all users from the repository
        List<User> users = userRepository.findAll();
        
        // Create a list to hold the groups
        List<List<User>> matchedGroups = new ArrayList<>();

        // Step 1: Scenario 1: Group users by similar subjects first
        Map<String, List<User>> usersBySubject = groupUsersBySubject(users);

        // Log the grouped users by subject
        System.out.println("Grouped users by subject:");
        for (String subject : usersBySubject.keySet()) {
            System.out.println("Subject: " + subject);
            for (User user : usersBySubject.get(subject)) {
                System.out.println(user.getName() + " (" + user.getEmail() + ")");
            }
        }

        // Step 2: Match users based on overlapping availability
        Map<String, List<User>> ungroupedUsers = new HashMap<>();
        for (List<User> subjectGroup : usersBySubject.values()) {
            // Group users by availability
            Map<String, List<User>> availabilityGroups = groupUsersByAvailability(subjectGroup);

            // Create groups based on availability and subject
            for (List<User> availabilityGroup : availabilityGroups.values()) {
                List<List<User>> createdGroups = createGroups(availabilityGroup);

                // Filter out groups with fewer than 2 members
                createdGroups.removeIf(group -> group.size() < 2);

                // Add valid groups to matchedGroups
                matchedGroups.addAll(createdGroups);

                // Track users who couldn't be grouped
                for (User user : availabilityGroup) {
                    if (!isUserGrouped(user, createdGroups)) {
                        ungroupedUsers.computeIfAbsent("ungrouped", k -> new ArrayList<>()).add(user);
                    }
                }
            }
        }

        

        // Log the final matched groups (only those with 2 or more members)
        System.out.println("Final matched groups:");
        for (List<User> group : matchedGroups) {
            if (group.size() >= 2) { // Log only groups with at least 2 members
                System.out.println("Group:");
                for (User user : group) {
                    System.out.println(user.getName() + " (" + user.getEmail() + ")");
                }
            }
        }

        return matchedGroups;
    }

    // Step 1 Helper: Group users by subject
    private Map<String, List<User>> groupUsersBySubject(List<User> users) {
        Map<String, List<User>> usersBySubject = new HashMap<>();

        for (User user : users) {
            for (Subject subject : user.getSubjects()) {
                String subjectName = subject.getName();
                if (!usersBySubject.containsKey(subjectName)) {
                    usersBySubject.put(subjectName, new ArrayList<>());
                }
                usersBySubject.get(subjectName).add(user);
            }
        }

        return usersBySubject;
    }

    // Step 2 Helper: Group users by availability
    private Map<String, List<User>> groupUsersByAvailability(List<User> users) {
        Map<String, List<User>> availabilityGroups = new HashMap<>();

        for (User user : users) {
            for (String availability : user.getAvailability()) {
                if (!availabilityGroups.containsKey(availability)) {
                    availabilityGroups.put(availability, new ArrayList<>());
                }
                availabilityGroups.get(availability).add(user);
            }
        }

        return availabilityGroups;
    }

    // Helper method to create groups based on availability and subject
    private List<List<User>> createGroups(List<User> availabilityGroup) {
        List<List<User>> groups = new ArrayList<>();
        int groupSize = availabilityGroup.size(); 

        for (int i = 0; i < availabilityGroup.size(); i += groupSize) {
            int end = Math.min(i + groupSize, availabilityGroup.size());
            List<User> group = availabilityGroup.subList(i, end);
            groups.add(group);
        }

        return groups;
    }

    // Helper method to check if a user is grouped
    private boolean isUserGrouped(User user, List<List<User>> createdGroups) {
        for (List<User> group : createdGroups) {
            if (group.contains(user)) {
                return true;
            }
        }
        return false;
    }
}
