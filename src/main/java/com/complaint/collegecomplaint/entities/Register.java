package com.complaint.collegecomplaint.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    private String name;
    private String email;
    private String password;
    private String phone;
}
