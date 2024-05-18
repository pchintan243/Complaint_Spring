package com.complaint.collegecomplaint.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.complaint.collegecomplaint.entities.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    List<Complaint> getAllComplaintsByEmail(String email);

    Complaint getComplaintById(int id);
}
