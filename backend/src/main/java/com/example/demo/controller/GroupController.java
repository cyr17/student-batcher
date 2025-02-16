package com.example.demo.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Group;
import com.example.demo.model.User;
import com.example.demo.repository.GroupRepository;
import com.example.demo.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupRepository groupRepository;
    private final GroupService groupService;

    public GroupController(GroupRepository groupRepository, GroupService groupService) {
        this.groupRepository = groupRepository;
        this.groupService = groupService;
    }
    
    @PostMapping("/")
    public Group createGroup(@RequestBody Group group) {
        return groupRepository.save(group);
    }

    @GetMapping("/")
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Group> getGroupById(@PathVariable String id) {
        return groupRepository.findById(id);
    }

    @GetMapping("/match")
    public List<List<User>> getMatchedGroups() {
        return groupService.matchUsersBySubjectAndAvailability();
    }

    @PostMapping("/allocate")
    public List<Group> allocateGroups() {
        return groupService.allocateGroups();
    }

    @PutMapping("/{id}")
    public String updateGroup(@PathVariable String id, @RequestBody Group group) {
        // update group
        Optional<Group> existingGroupRef = groupRepository.findById(id);
        if (existingGroupRef.isPresent()) {
            Group existingGroup = existingGroupRef.get();
            existingGroup.setUserIds(group.getUserIds());
            groupRepository.save(existingGroup);
            return "Group updated successfully!";
        } else {
            return "Group not found!";
        }

    }

    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable String id) {
        groupRepository.deleteById(id);
        return "Group deleted successfully!!";
    }
}
