package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Group;
import com.example.demo.model.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;

@Service
public class GroupService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> matchUsersIntoGroups(int maxGroupSize){
        List<User> users = userRepository.findAll();

        // Group users by subjects
         Map<String, List<User>> usersBySubject = users.stream()
                .flatMap(user -> user.getSubjects().stream().map(subject -> Map.entry(subject, user)))
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

        
        List<Group> studyGroups = new ArrayList<>();

         // Step 2: Within each subject, group users by overlapping availability
        for (Map.Entry<String, List<User>> entry : usersBySubject.entrySet()) {
            String subject = entry.getKey();
            List<User> subjectUsers = entry.getValue();

            // Map of timeslots -> list of users available at that time
            Map<String, List<User>> timeSlotGroups = new HashMap<>();

            for (User user : subjectUsers) {
                for (String timeSlot : user.getAvailability()) {
                    timeSlotGroups.computeIfAbsent(timeSlot, k -> new ArrayList<>()).add(user);
                }
            }

            // Step 3: Create groups with users who share time slots
            for (Map.Entry<String, List<User>> timeSlotEntry : timeSlotGroups.entrySet()) {
                String timeSlot = timeSlotEntry.getKey();
                List<User> availableUsers = timeSlotEntry.getValue();

                // Split into groups of maxGroupSize
                for (int i = 0; i < availableUsers.size(); i += maxGroupSize) {
                    List<User> groupUsers = availableUsers.subList(i, Math.min(i + maxGroupSize, availableUsers.size()));
                    List<String> userIds = groupUsers.stream().map(User::getId).collect(Collectors.toList());

                    Group group = new Group(subject, timeSlot, userIds);
                    studyGroups.add(group);
                }
            }
        }

        // Save groups to database
        return groupRepository.saveAll(studyGroups);
        
    }
}
