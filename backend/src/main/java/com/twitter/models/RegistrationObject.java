package com.twitter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegistrationObject {
    private String firstName;
    private String lastName;
    private String email;
    private Date dob;
}
