package com.docTime.service;

import com.docTime.dto.PatientRequestDTO;
import com.docTime.dto.PatientResponseDTO;
import com.docTime.dto.PatientUpdateDTO;
import com.docTime.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    PatientRequestDTO createPatient(PatientRequestDTO patient);
    //Page<PatientResponseDTO> getAllPatients(Pageable pageable);
    List<PatientResponseDTO> getAllPatients();
    PatientResponseDTO getPatientById(Long id);
    PatientResponseDTO getPatientParEmail(String email);
    Page<PatientResponseDTO> searchPatients(String query, Pageable pageable);
    PatientResponseDTO updatePatient(Long id, PatientUpdateDTO dto);
    PatientResponseDTO deletePatient(Long id);
}
