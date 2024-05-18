package com.complaint.collegecomplaint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.complaint.collegecomplaint.entities.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
}
