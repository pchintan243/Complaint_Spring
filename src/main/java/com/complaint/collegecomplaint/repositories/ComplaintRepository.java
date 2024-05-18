package com.complaint.collegecomplaint.repositories;

import org.springframework.data.repository.CrudRepository;

import com.complaint.collegecomplaint.entities.Complaint;

public interface ComplaintRepository extends CrudRepository<Complaint, Integer> {

}
