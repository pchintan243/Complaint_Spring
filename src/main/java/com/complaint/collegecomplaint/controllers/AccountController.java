package com.complaint.collegecomplaint.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.complaint.collegecomplaint.entities.AppUser;
import com.complaint.collegecomplaint.entities.JwtResponse;
import com.complaint.collegecomplaint.entities.Login;
import com.complaint.collegecomplaint.entities.Register;
import com.complaint.collegecomplaint.services.AccountService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "BearerAuth")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/auth/userLogin")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody Login login) {

        JwtResponse response = accountService.userLogin(login);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/auth/userRegister")
    public ResponseEntity<AppUser> registerUser(@RequestBody Register register) {
        AppUser registerUser = accountService.registerUser(register);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @PostMapping("/auth/adminLogin")
    public ResponseEntity<JwtResponse> adminLogin(@RequestBody Login login) {
        JwtResponse adminLogin = accountService.adminLogin(login);
        if (adminLogin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(adminLogin);
    }

    @PostMapping("/auth/adminRegister")
    public ResponseEntity<AppUser> registerAdmin(@RequestBody Register register) {
        AppUser registerAdmin = accountService.registerAdmin(register);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerAdmin);
    }

    @GetMapping("/api/getAllUsers")
    public ResponseEntity<List<AppUser>> getAllUsers() {

        return ResponseEntity.ok().body(accountService.getAllUsers());
    }

}
