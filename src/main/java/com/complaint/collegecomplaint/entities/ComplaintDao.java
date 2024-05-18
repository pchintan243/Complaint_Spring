package com.complaint.collegecomplaint.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintDao {
    private String Department;
    private String query;
    private String otherQuery;
    private String computerIp;
    private String note;
}
