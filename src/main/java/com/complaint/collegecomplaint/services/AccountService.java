package com.complaint.collegecomplaint.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.complaint.collegecomplaint.entities.AppUser;
import com.complaint.collegecomplaint.entities.Claims;
import com.complaint.collegecomplaint.entities.Register;
import com.complaint.collegecomplaint.repositories.AccountRepository;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // public ResponseEntity<Login> login(Login login) {

    // }

    public AppUser registerUser(Register register) {
        AppUser appUser = new AppUser();
        appUser.setName(register.getName());
        appUser.setEmail(register.getEmail());
        appUser.setPassword(passwordEncoder.encode(register.getPassword()));
        appUser.setPhone(register.getPhone());

        Claims role = new Claims();
        role.setEmail(register.getEmail());
        role.setClaims("USER");
        role.setUser(appUser);

        appUser.setClaims(role);
        AppUser userData = accountRepository.save(appUser);
        return userData;
    }

}
