package com.docTime.service;

import com.docTime.dto.PatientRequestDTO;
import com.docTime.dto.PatientResponseDTO;
import com.docTime.dto.PatientUpdateDTO;
import com.docTime.mapper.PatientMapper;
import com.docTime.model.Patient;
import com.docTime.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService{
    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientMapper patientMapper, PatientRepository patientRepository) {
        this.patientMapper = patientMapper;
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientRequestDTO createPatient(PatientRequestDTO patient) {
          patientRepository.save(patientMapper.toEntity(patient));
          return patient;
    }

//
//        @Override
//        @Transactional(readOnly = true)
//        public Page<PatientResponseDTO> getAllPatients(Pageable pageable) {
//           Page<Patient> pageDePatients = patientRepository.findAll(pageable);
//
//             return pageDePatients.map(patient -> patientMapper.toDTO(patient));
//
//    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patient->patientMapper.toDTO(patient)).collect(Collectors.toList());

    }

    @Override
    public Page<PatientResponseDTO> searchPatients(String query, Pageable pageable) {
        Page<Patient> patients = patientRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query, pageable);

        return patients.map(patientMapper::toDTO);
    }

    @Override
    public PatientResponseDTO getPatientById(Long id) {
        return patientMapper.toDTO(
                patientRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Le patient n'existe pas avec Id"+id))
        );
    }

    @Override
    public PatientResponseDTO getPatientParEmail(String email) {
        Patient patient =   patientRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Le patient a n'existe pas avec Email "+email));
         return patientMapper.toDTO(patient);
    }


    @Override
    public PatientResponseDTO updatePatient(Long id, PatientUpdateDTO dto) {
        Patient patientExistant = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient avec ID "+id+" introuvable"));

        patientMapper.updatePatientFromDto(dto, patientExistant);
        patientRepository.save(patientExistant);
        return patientMapper.updateResponseDTOFromPatientUpdateDTO(dto,patientMapper.toDTO(patientExistant));
    }

    @Override
    public PatientResponseDTO deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impossible de supprimer : Patient introuvable avec l'ID " + id));
        patientRepository.delete(patient);

        return patientMapper.toDTO(patient);
    }
}
