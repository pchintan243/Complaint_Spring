package com.complaint.collegecomplaint.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {
    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String firstName;
    private String lastName;
    private String email;
    private String Department;
    private String query;
    @Column(nullable = true)
    private String otherQuery;
    @Column(nullable = true)
    private String computerIp;
    private String phone;
    private String note;
    private Date date;
    private String status;
    private String flag;
}
