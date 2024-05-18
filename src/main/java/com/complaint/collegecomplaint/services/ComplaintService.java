package com.complaint.collegecomplaint.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.complaint.collegecomplaint.entities.AppUser;
import com.complaint.collegecomplaint.entities.Complaint;
import com.complaint.collegecomplaint.entities.ComplaintDao;
import com.complaint.collegecomplaint.helper.JwtHelper;
import com.complaint.collegecomplaint.repositories.AccountRepository;
import com.complaint.collegecomplaint.repositories.ComplaintRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private HttpServletRequest request;

    public List<Complaint> getAllComplaints() {
        return (List<Complaint>) complaintRepository.findAll();
    }

    public Complaint registerComplaint(ComplaintDao complaintDao) {
        Complaint comp = new Complaint();
        comp.setDepartment(complaintDao.getDepartment());
        comp.setNote(complaintDao.getNote());

        comp.setQuery(complaintDao.getQuery());
        if (complaintDao.getQuery().equals("computer")) {
            comp.setComputerIp(complaintDao.getComputerIp());
        }
        if (complaintDao.getQuery().equals("otherquery")) {
            comp.setOtherQuery(complaintDao.getOtherQuery());
        }

        String requestHeader = request.getHeader("Authorization");
        String token = null;
        token = requestHeader.substring(7);
        String email = helper.getUsernameFromToken(token);

        AppUser fetchUser = accountRepository.findByEmail(email);

        comp.setEmail(email);
        comp.setName(fetchUser.getName());
        comp.setPhone(fetchUser.getPhone());
        comp.setDepartment("it");

        return complaintRepository.save(comp);
    }
}
