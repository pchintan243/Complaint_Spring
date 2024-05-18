package com.complaint.collegecomplaint.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.complaint.collegecomplaint.entities.AppUser;
import com.complaint.collegecomplaint.entities.JwtResponse;
import com.complaint.collegecomplaint.entities.Login;
import com.complaint.collegecomplaint.entities.Register;
import com.complaint.collegecomplaint.helper.JwtHelper;
import com.complaint.collegecomplaint.services.AccountService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuth")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/auth/userLogin")
    public ResponseEntity<JwtResponse> login(@RequestBody Login login) {
        this.doAuthenticate(login.getEmail(), login.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());

        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .token(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/auth/userRegister")
    public ResponseEntity<AppUser> registerUser(@RequestBody Register register) {
        AppUser registerUser = accountService.registerUser(register);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    // @GetMapping("/getAllUsers")
    // public ResponseEntity<List<Register>> getAllUsers() {

    // return ResponseEntity.ok().body(accountService.getAllUsers());
    // }

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
