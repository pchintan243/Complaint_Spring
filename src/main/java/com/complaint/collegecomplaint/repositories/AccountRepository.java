package com.complaint.collegecomplaint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.complaint.collegecomplaint.entities.AppUser;

@Repository
public interface AccountRepository extends JpaRepository<AppUser, Integer> {
    public AppUser findById(int id);

    public AppUser findByEmail(String email);
}
