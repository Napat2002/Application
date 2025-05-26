package com.example.REST_API.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.REST_API.models.Application;
import com.example.REST_API.services.ApplicationService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/create")
    public ResponseEntity<?> createApplication(@RequestBody Application application) {
        Application created = applicationService.createApplication(application);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long id) {
        Optional<Application> app = applicationService.getApplicationById(id);
        if (app.isPresent()) {
            return ResponseEntity.ok(app.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบข้อมูลใบสมัครที่ระบุ");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateApplication(@PathVariable Long id, @RequestBody Application data) {
        Optional<Application> updated = applicationService.updateApplication(id, data);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบข้อมูลใบสมัครที่ต้องการแก้ไข");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteApplication(@PathVariable Long id) {
        boolean deleted = applicationService.deleteApplication(id);
        if (deleted) {
            return ResponseEntity.ok("ลบข้อมูลใบสมัครเรียบร้อยแล้ว");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบข้อมูลใบสมัครที่ต้องการลบ");
        }
        
    }
}
