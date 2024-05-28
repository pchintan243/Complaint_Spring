package com.complaint.collegecomplaint.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.complaint.collegecomplaint.entities.Complaint;
import com.complaint.collegecomplaint.entities.ComplaintDao;
import com.complaint.collegecomplaint.services.ComplaintService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@SecurityRequirement(name = "BearerAuth")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/complaint/registerComplaint")
    public ResponseEntity<Complaint> registerComplaint(@RequestBody ComplaintDao dao) {
        Complaint complaint = complaintService.registerComplaint(dao);
        return ResponseEntity.status(HttpStatus.CREATED).body(complaint);
    }

    @GetMapping("/complaint/getAllComplaints")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> allComplaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(allComplaints);
    }

    @GetMapping("/complaint/{id}")
    public ResponseEntity<Complaint> getAllComplaints(@PathVariable int id) {
        Complaint complaint = complaintService.getComplaintById(id);
        if (complaint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(complaint);
    }

    @PostMapping("/complaint/count")
    public ResponseEntity<?> getCountofComplaint() {
        Long countOfComplaint = complaintService.getCountofComplaint();

        Map<String, Long> response = new HashMap<>();
        response.put("count", countOfComplaint);

        return ResponseEntity.ok(response);
    }

    @PutMapping("complaint/updateComplaint/{id}")
    public ResponseEntity<Complaint> updateComplaint(@RequestBody ComplaintDao dao, @PathVariable int id) {
        Complaint updateComplaint = complaintService.updateComplaint(dao, id);
        if (updateComplaint == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(updateComplaint);
    }

    @DeleteMapping("complaint/deleteComplaint/{id}")
    public ResponseEntity<?> deleteComplaint(@PathVariable int id) {
        Boolean complaint = complaintService.deleteComplaint(id);
        if (!complaint) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("complaint/proceed/{id}")
    public ResponseEntity<Complaint> proceedComplaint(@PathVariable int id) {
        Complaint comp = complaintService.proceedComplaint(id);
        if (comp != null)
            return ResponseEntity.ok(comp);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("complaint/solved/{id}")
    public ResponseEntity<Complaint> solvedComplaint(@PathVariable int id) {
        Complaint comp = complaintService.solvedComplaint(id);
        return ResponseEntity.ok(comp);
    }
}
