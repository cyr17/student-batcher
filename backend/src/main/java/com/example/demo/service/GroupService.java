package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Group;
import com.example.demo.model.Subject;
import com.example.demo.model.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.UserRepository;

@Service
public class GroupService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GroupRepository groupRepository;

    public List<List<User>> matchUsersBySubjectAndAvailability() {
        List<User> users = userRepository.findAll();
        List<List<User>> matchedGroups = new ArrayList<>();

        //group by subject first
        Map<String, List<User>> usersBySubject = groupUsersBySubject(users);

        for (List<User> subjectGroup : usersBySubject.values()) {

            //group these groups by availability
            Map<String, List<User>> availabilityGroups = groupUsersByAvailability(subjectGroup);

            for (List<User> availabilityGroup : availabilityGroups.values()) {
                List<List<User>> createdGroups = createGroups(availabilityGroup);

                // remove groups with less than 2 users
                createdGroups.removeIf(group -> group.size() < 2);

                matchedGroups.addAll(createdGroups);
            }
        }

        return matchedGroups;
    }

    public List<Group> allocateGroups() {
        
        List<List<User>> matchedGroups = matchUsersBySubjectAndAvailability();
        List<Group> savedGroups = new ArrayList<>();
    
        for (List<User> group : matchedGroups) {
            if (group.isEmpty()) continue;
    
            // Find common subjects
            List<String> commonSubjects = findCommonSubjects(group);
            if (commonSubjects.isEmpty()) continue; // Skip if no common subjects
    
            // Find common time slots
            List<String> commonTimeSlots = findCommonTimeSlots(group);
            if (commonTimeSlots.isEmpty()) continue; // Skip if no common availability
    
            // Pick the common subject
            String subject = commonSubjects.get(0);
            
            // Pick the common time slot
            String timeSlot = commonTimeSlots.get(0); 
    
            //fetch user ids
            List<String> userIds = group.stream().map(User::getId).collect(Collectors.toList());
    
            //check if group combination already exists
            Optional<Group> existingGroup = groupRepository.findAll().stream()
                .filter(g -> g.getUserIds().containsAll(userIds) && g.getUserIds().size() == userIds.size())
                .findFirst();
    
            //create a new group
            if (existingGroup.isEmpty()) {
                Group newGroup = new Group(subject, timeSlot, userIds);
                savedGroups.add(groupRepository.save(newGroup));
            }
        }
        return savedGroups;
    }

    // USERS WITH SUBJECT DATA TO --> "SUBJECT" : [USER1..,USERN]
    private Map<String, List<User>> groupUsersBySubject(List<User> users) {
        Map<String, List<User>> usersBySubject = new HashMap<>();
        for (User user : users) {
            for (Subject subject : user.getSubjects()) {
                usersBySubject.computeIfAbsent(subject.getName(), k -> new ArrayList<>()).add(user);
            }
        }
        return usersBySubject;
    }

    
    // USERS WITH AVAILABILITY DATA TO --> "TIMESLOT" : [USER1..,USERN]
    private Map<String, List<User>> groupUsersByAvailability(List<User> users) {
        Map<String, List<User>> availabilityGroups = new HashMap<>();
        for (User user : users) {
            for (String availability : user.getAvailability()) {
                availabilityGroups.computeIfAbsent(availability, k -> new ArrayList<>()).add(user);
            }
        }
        return availabilityGroups;
    }

    private List<List<User>> createGroups(List<User> availabilityGroup) {
        List<List<User>> groups = new ArrayList<>();
        int groupSize = availabilityGroup.size();
        for (int i = 0; i < availabilityGroup.size(); i += groupSize) {
            int end = Math.min(i + groupSize, availabilityGroup.size());
            groups.add(availabilityGroup.subList(i, end));
        }
        return groups;
    }

    private List<String> findCommonTimeSlots(List<User> users) {
        if (users.isEmpty()) return new ArrayList<>();
    
        List<String> commonTimeSlots = new ArrayList<>(users.get(0).getAvailability());
    
        for (User user : users) {
            commonTimeSlots.retainAll(user.getAvailability());
        }
        return commonTimeSlots;
    }
    
    private List<String> findCommonSubjects(List<User> users) {
        if (users.isEmpty()) return new ArrayList<>();
    
        List<String> commonSubjects = new ArrayList<>(users.get(0).getSubjects().stream()
            .map(Subject::getName)
            .collect(Collectors.toList()));
    
        for (User user : users) {
            List<String> userSubjects = user.getSubjects().stream()
                .map(Subject::getName)
                .collect(Collectors.toList());
    
            commonSubjects.retainAll(userSubjects);
        }
        return commonSubjects;
    }
    
}
