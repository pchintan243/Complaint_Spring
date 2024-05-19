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

        String email = getEmailFromToken();

        AppUser fetchUser = accountRepository.findByEmail(email);

        comp.setEmail(email);
        comp.setName(fetchUser.getName());
        comp.setPhone(fetchUser.getPhone());

        return complaintRepository.save(comp);
    }

    public List<Complaint> getAllComplaints() {
        String email = getEmailFromToken();
        List<Complaint> allComplaints = complaintRepository.getAllComplaintsByEmail(email);
        return allComplaints;
    }

    public Complaint getComplaintById(int id) {
        Complaint complaint = complaintRepository.getComplaintById(id);
        String email = getEmailFromToken();
        if (complaint.getEmail().equals(email)) {
            return complaint;
        }
        return null;
    }

    public Long getCountofComplaint() {
        String email = getEmailFromToken();
        long count = complaintRepository.countByEmail(email);
        return count;
    }

    public Complaint updateComplaint(ComplaintDao dao, int id) {
        Complaint existingComplaint = complaintRepository.getComplaintById(id);
        if (existingComplaint == null) {
            return null;
        }

        String email = getEmailFromToken();
        if (!email.equals(existingComplaint.getEmail())) {
            return null;
        }

        existingComplaint.setDepartment(dao.getDepartment());
        existingComplaint.setNote(dao.getNote());
        existingComplaint.setQuery(dao.getQuery());
        if (dao.getQuery().equals("computer")) {
            existingComplaint.setComputerIp(dao.getComputerIp());
        }
        if (dao.getQuery().equals("otherquery")) {
            existingComplaint.setOtherQuery(dao.getOtherQuery());
        }

        return complaintRepository.save(existingComplaint);
    }

    public Boolean deleteComplaint(int id) {
        Complaint existingComplaint = complaintRepository.getComplaintById(id);
        if (existingComplaint == null) {
            return false;
        }

        String email = getEmailFromToken();
        if (!email.equals(existingComplaint.getEmail())) {
            return false;
        }

        complaintRepository.delete(existingComplaint);
        return true;
    }

    private String getEmailFromToken() {
        String requestHeader = request.getHeader("Authorization");
        String token = null;
        token = requestHeader.substring(7);
        String email = helper.getUsernameFromToken(token);
        return email;
    }

}
