package com.complaint.collegecomplaint.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.collegecomplaint.entities.Complaint;
import com.complaint.collegecomplaint.repositories.ComplaintRepository;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaints() {
        return (List<Complaint>) complaintRepository.findAll();
    }

    public Complaint addComplaint(Complaint complaint) {
        
        return complaintRepository.save(complaint);
    }
}
