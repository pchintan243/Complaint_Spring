package com.complaint.collegecomplaint.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.complaint.collegecomplaint.entities.AppUser;
import com.complaint.collegecomplaint.entities.Claims;
import com.complaint.collegecomplaint.entities.JwtResponse;
import com.complaint.collegecomplaint.entities.Login;
import com.complaint.collegecomplaint.entities.Register;
import com.complaint.collegecomplaint.helper.JwtHelper;
import com.complaint.collegecomplaint.repositories.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserDetailsService userDetailsService;

    public JwtResponse loginUser(Login login) {

        JwtResponse response = authenticateDetail(login);
        return response;
    }

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

    public List<AppUser> getAllUsers() {
        return (List<AppUser>) accountRepository.findAll();
    }

    private JwtResponse authenticateDetail(Login login) {
        this.doAuthenticate(login.getEmail(), login.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());

        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .token(token)
                .username(userDetails.getUsername()).build();
        return response;
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

}
