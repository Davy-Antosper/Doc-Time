package com.docTime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PatientUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
}
