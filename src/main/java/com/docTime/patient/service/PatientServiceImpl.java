package com.docTime.patient.service;

import com.docTime.patient.dto.PatientRequestDTO;
import com.docTime.patient.dto.PatientResponseDTO;
import com.docTime.patient.dto.PatientUpdateDTO;
import com.docTime.patient.mapper.PatientMapper;
import com.docTime.patient.model.Patient;
import com.docTime.patient.repository.PatientRepository;
import com.docTime.validation.ErrorType;
import com.docTime.validation.ValidationException;
import com.docTime.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {
    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;
    private final Validator validator;


    public PatientServiceImpl(PatientMapper patientMapper, PatientRepository patientRepository, Validator validator) {
        this.patientMapper = patientMapper;
        this.patientRepository = patientRepository;
        this.validator = validator;
    }

    @Override
    @Transactional
    public PatientResponseDTO createPatient(PatientRequestDTO dto) {
        validator.validate(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhone(), dto.getCountryCode());
        //validator.validate(dto.getFirstName(), dto.getLastName(), dto.getCountryCode());

        log.info("Tentative de création d'un nouveau patient dans le système");

        Patient patient = patientMapper.toEntity(dto);
        patientRepository.save(patient);

        log.info("Patient créé avec succès. ID : {}", patient.getId());
        return patientMapper.toDTO(patient);
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
        log.info("Appel Affichage de tout les Patients ");
        return patientRepository.findAll().stream()
                .map(patient->patientMapper.toDTO(patient)).collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientResponseDTO> searchPatients(String query, Pageable pageable) {
        log.info("Appel Affichage du Patient avec Query ");
        Page<Patient> patients = patientRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query, pageable);

        return patients.map(patientMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDTO getPatientById(Long id) {
        log.info("Appel Affichage du Patient avec ID {} ",id);
        return patientMapper.toDTO(
                patientRepository.findById(id)
                .orElseThrow(()->new ValidationException(ErrorType.RESOURCE_NOT_FOUND,"Le patient avec l'identifiant " + id + " n'existe pas."))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PatientResponseDTO getPatientParEmail(String email) {
        log.info("Recherche du patient par email ");
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new ValidationException(
                        ErrorType.RESOURCE_NOT_FOUND,
                        "Aucun patient trouvé avec cet adresse email: "
                ));
         return patientMapper.toDTO(patient);
    }


    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientUpdateDTO dto) {
        Patient patientExistant = patientRepository.findById(id)
                .orElseThrow(()->new ValidationException(ErrorType.RESOURCE_NOT_FOUND,"Le patient avec l'identifiant " + id + " n'existe pas."));

        patientMapper.updatePatientFromDto(dto, patientExistant);

        validator.validate(patientExistant.getFirstName(), patientExistant.getLastName(), patientExistant.getEmail(), patientExistant.getPhone(), patientExistant.getCountryCode());

        return patientMapper.toDTO(patientExistant);
    }



    @Override
    @Transactional
    public PatientResponseDTO deletePatient(Long id) {
        log.info("Delete du Patient avec ID {} ",id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ValidationException(
                        ErrorType.RESOURCE_NOT_FOUND,
                        "Suppression impossible : Patient ID " + id + " introuvable."
                ));
        patientRepository.delete(patient);

        return patientMapper.toDTO(patient);
    }

    @Override
    public PatientResponseDTO findById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(()->new ValidationException(ErrorType.RESOURCE_NOT_FOUND,"Le patient avec l'identifiant " + patientId + " n'existe pas."));
        ;
    }
}
