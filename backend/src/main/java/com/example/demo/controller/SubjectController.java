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

import com.example.demo.model.Subject;
import com.example.demo.repository.SubjectRepository;


@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    
    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    //CREATE
    @PostMapping("/create")
    public Subject createSubject(@RequestBody Subject subject) {
        
        return subjectRepository.save(subject);
    }

    //GET 
    @GetMapping("/")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

     @GetMapping("/{id}")
    public Optional<Subject> getSubjectById(@PathVariable String id) {
        return subjectRepository.findById(id);
    }

    //UPDATE
    @PutMapping("/{id}")
    public String updateSubject(@PathVariable String id, @RequestBody Subject updatedSubject) {
        Optional<Subject> existingSubjectRef = subjectRepository.findById(id);
        if (existingSubjectRef.isPresent()) {
            Subject existingSubject = existingSubjectRef.get();
            existingSubject.setName(updatedSubject.getName());
            subjectRepository.save(existingSubject);
            return "Subject updated successfully!";
        } else {
            return "Subject not found!";
        }
    }

    //DELETE
    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable String id) {
        subjectRepository.deleteById(id);
        return "Subject deleted successfully!";
    }


    
}
