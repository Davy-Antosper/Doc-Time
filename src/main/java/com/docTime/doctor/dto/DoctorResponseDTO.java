package com.docTime.doctor.dto;

import com.docTime.disponibilite.model.Disponibilite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DoctorResponseDTO {
     Long id;
     String firstName;
     String lastName;
     String phone;
     String email;
     String countryCode;
     String specialty;
     List<Disponibilite> availabilities;
}
