package com.docTime.doctor.service;

import com.docTime.disponibilite.dto.DisponibiliteRequestDTO;
import com.docTime.disponibilite.dto.DisponibiliteResponseDTO;
import com.docTime.doctor.dto.*;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    DoctorResponseDTO createDoctor(DoctorRequestDTO dto);
    List<DoctorResponseDTO> getAllDoctors();
    DoctorResponseDTO getDoctorById(Long id);
    DoctorResponseDTO updateDoctor(Long id, DoctorUpdateRequestDTO dto);
    void deleteDoctor(Long id);

    DisponibiliteResponseDTO addDisponibilite(Long doctorId, DisponibiliteRequestDTO dto);

    List<DoctorResponseDTO> getDoctorsBySpecialty(String specialty);
   // List<DoctorResponseDTO> searchDoctors(String keyword);
    DoctorResponseDTO getDoctorByEmail(String email);
    boolean existsById(Long id);

    DoctorResponseDTO findById(Long doctorId);
}
