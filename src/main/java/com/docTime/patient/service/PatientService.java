package com.docTime.patient.service;

import com.docTime.patient.dto.PatientRequestDTO;
import com.docTime.patient.dto.PatientResponseDTO;
import com.docTime.patient.dto.PatientUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    PatientResponseDTO createPatient(PatientRequestDTO patient);
    //Page<PatientResponseDTO> getAllPatients(Pageable pageable);
    List<PatientResponseDTO> getAllPatients();
    PatientResponseDTO getPatientById(Long id);
    PatientResponseDTO getPatientParEmail(String email);
    Page<PatientResponseDTO> searchPatients(String query, Pageable pageable);
    PatientResponseDTO updatePatient(Long id, PatientUpdateDTO dto);
    PatientResponseDTO deletePatient(Long id);

    PatientResponseDTO findById(Long patientId);
}
