package com.docTime.doctor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DoctorUpdateRequestDTO {
    String firstName;
    String lastName;
    String phone;
    String email;
    String countryCode;
    String specialty;
}
