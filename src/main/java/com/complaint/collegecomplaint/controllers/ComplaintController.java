package com.complaint.collegecomplaint.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.complaint.collegecomplaint.entities.Complaint;
import com.complaint.collegecomplaint.entities.ComplaintDao;
import com.complaint.collegecomplaint.services.ComplaintService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuth")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/auth/complaint")
    public Complaint registerComplaint(@RequestBody ComplaintDao dao) {
        Complaint complaint = complaintService.registerComplaint(dao);
        return complaint;
    }
}
