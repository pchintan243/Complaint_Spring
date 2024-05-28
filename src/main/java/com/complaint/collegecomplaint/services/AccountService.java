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

    public JwtResponse userLogin(Login login) {
        JwtResponse response = authenticateDetail(login);
        return response;
    }

    public AppUser registerUser(Register register) {
        String role = "User";
        AppUser userData = setUserDetails(register, role);
        return userData;
    }

    public JwtResponse adminLogin(Login login) {

        AppUser user = accountRepository.findByEmail(login.getEmail());

        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Administrator"))) {
            JwtResponse response = authenticateDetail(login);
            return response;
        }
        return null;
    }

    public AppUser registerAdmin(Register register) {
        String role = "Administrator";
        AppUser admin = setUserDetails(register, role);
        return admin;
    }

    public JwtResponse astLogin(Login login) {
        AppUser user = accountRepository.findByEmail(login.getEmail());

        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("AssistantManager"))) {
            JwtResponse response = authenticateDetail(login);
            return response;
        }
        return null;
    }

    public AppUser registerAssistant(Register register) {
        String role = "AssistantManager";
        AppUser astUser = setUserDetails(register, role);
        return astUser;
    }

    public JwtResponse headLogin(Login login) {
        AppUser user = accountRepository.findByEmail(login.getEmail());

        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("FacultyHead"))) {
            JwtResponse response = authenticateDetail(login);
            return response;
        }
        return null;
    }

    public AppUser registerHead(Register register) {
        String role = "FacultyHead";
        AppUser headUser = setUserDetails(register, role);
        return headUser;
    }

    public List<AppUser> getAllUsers() {
        return (List<AppUser>) accountRepository.findAll();
    }

    private AppUser setUserDetails(Register register, String role) {
        AppUser appUser = new AppUser();
        appUser.setName(register.getName());
        appUser.setEmail(register.getEmail());
        appUser.setPassword(passwordEncoder.encode(register.getPassword()));
        appUser.setPhone(register.getPhone());

        Claims claims = new Claims();
        claims.setEmail(register.getEmail());
        claims.setClaims(role);
        claims.setUser(appUser);

        appUser.setClaims(claims);
        AppUser userData = accountRepository.save(appUser);
        return userData;
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
