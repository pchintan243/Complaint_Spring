package com.complaint.collegecomplaint.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.complaint.collegecomplaint.entities.Complaint;
import com.complaint.collegecomplaint.entities.ComplaintDao;
import com.complaint.collegecomplaint.services.ComplaintService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
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

}
