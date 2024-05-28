package com.complaint.collegecomplaint.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.complaint.collegecomplaint.entities.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {
    @Query("SELECT e FROM Complaint e WHERE e.email = :email ORDER BY e.id DESC")
    List<Complaint> getAllComplaints(String email);

    @Query("SELECT e FROM Complaint e ORDER BY e.id DESC")
    List<Complaint> getAllComplaintsOrderById();

    Complaint getComplaintById(int id);

    long countByEmail(String email);

    long count();

}
