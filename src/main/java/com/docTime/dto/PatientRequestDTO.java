package com.docTime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class PatientRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthDate;
}
