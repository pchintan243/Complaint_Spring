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
    private int id;
    private String name;
    private String email;
    private String department;
    private String query;
    @Column(nullable = true)
    private String otherQuery;
    @Column(nullable = true)
    private String computerIp;
    private String phone;
    private String note;
    private String date = dateTime();
    private String status = "Pending";
    private String flag = "Pending";

    private String dateTime() {
        String date = new Date().toString();
        date = date.substring(0, 19);
        return date;

    }
}
