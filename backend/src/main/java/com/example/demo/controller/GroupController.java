package com.example.demo.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Group;
import com.example.demo.repository.GroupRepository;
import com.example.demo.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private GroupRepository groupRepository;

    private GroupService groupService;

    
    @PostMapping("/create")
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

    @PostMapping("/match")
    public List<Group> matchUsersIntoGroups(@RequestParam(defaultValue = "3") int maxSize) {
        return groupService.matchUsersIntoGroups(maxSize);
    }

    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable String id) {
        groupRepository.deleteById(id);
        return "Group deleted successfully!";
    }

    
}
