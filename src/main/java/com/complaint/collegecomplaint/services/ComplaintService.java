package com.complaint.collegecomplaint.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
        if (complaintDao.getQuery().equals("Computer")) {
            comp.setComputerIp(complaintDao.getComputerIp());
        }
        if (complaintDao.getQuery().equals("OtherQuery")) {
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

        boolean isAdmin = isAdmin(email);

        List<Complaint> allComplaints;
        if (isAdmin) {
            allComplaints = complaintRepository.getAllComplaintsOrderById();
        } else {

            allComplaints = complaintRepository.getAllComplaints(email);
        }
        return allComplaints;
    }

    public Complaint getComplaintById(int id) {
        Complaint complaint = complaintRepository.getComplaintById(id);
        String email = getEmailFromToken();
        boolean isAdmin = isAdmin(email);
        if (isAdmin) {
            return complaint;
        }
        if (complaint.getEmail().equals(email)) {
            return complaint;
        }
        return null;
    }

    public Long getCountofComplaint() {
        String email = getEmailFromToken();

        boolean isAdmin = isAdmin(email);
        if (isAdmin) {
            return complaintRepository.count();
        }

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

    public Complaint proceedComplaint(int id) {
        String email = getEmailFromToken();

        boolean isFaHead = isFaHead(email);

        Complaint comp = complaintRepository.getComplaintById(id);
        if (comp.getStatus().equals("Pending") && isFaHead) {
            comp.setStatus("Processing");
        }
        return complaintRepository.save(comp);
    }

    public Complaint solvedComplaint(int id) {

        String email = getEmailFromToken();
        boolean isAst = isAssistantManager(email);

        Complaint comp = complaintRepository.getComplaintById(id);
        if (comp.getStatus().equals("Processing") && isAst) {
            comp.setStatus("Solved");
        }
        return complaintRepository.save(comp);
    }

    private String getEmailFromToken() {
        String requestHeader = request.getHeader("Authorization");
        String token = null;
        token = requestHeader.substring(7);
        String email = helper.getUsernameFromToken(token);
        return email;
    }

    private boolean isAdmin(String email) {
        AppUser user = accountRepository.findByEmail(email);

        boolean isAdmin = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("Administrator"));

        return isAdmin;
    }

    private boolean isAssistantManager(String email) {
        AppUser user = accountRepository.findByEmail(email);

        boolean isAst = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("AssistantManager"));

        return isAst;
    }

    private boolean isFaHead(String email) {
        AppUser user = accountRepository.findByEmail(email);

        boolean isHead = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("FacultyHead"));

        return isHead;
    }

}
