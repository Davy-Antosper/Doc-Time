package com.docTime.doctor.service;

import com.docTime.disponibilite.dto.DisponibiliteRequestDTO;
import com.docTime.disponibilite.dto.DisponibiliteResponseDTO;
import com.docTime.disponibilite.mapper.DisponibiliteMapper;
import com.docTime.disponibilite.model.Disponibilite;
import com.docTime.doctor.dto.*;
import com.docTime.doctor.mapper.DoctorMapper;
import com.docTime.doctor.model.Doctor;
import com.docTime.doctor.repository.DoctorRepository;
import com.docTime.validation.ErrorType;
import com.docTime.validation.ValidationException;
import com.docTime.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@Service
public class DoctorServiceImpl implements DoctorService {

    private final Validator validator;
    private final DisponibiliteMapper disponibiliteMapper;
    private final DoctorMapper doctorMapper;
    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(Validator validator, DisponibiliteMapper disponibiliteMapper, DoctorMapper doctorMapper, DoctorRepository doctorRepository) {
        this.validator = validator;
        this.disponibiliteMapper = disponibiliteMapper;
        this.doctorMapper = doctorMapper;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    @Override
    public DoctorResponseDTO createDoctor(DoctorRequestDTO dto) {
        validator.validate(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getCountryCode()
        );
        log.info("Tentative de création d'un nouveau docteur dans le système");

        Doctor doctor = doctorMapper.doctorRequestToEntity(dto);
        doctorRepository.save(doctor);

        log.info("Docteur créé avec succès. ID : {}", doctor.getId());
        return doctorMapper.toResponse(doctor);
    }

    @Override
    public List<DoctorResponseDTO> getAllDoctors() {
        log.info("Appel Affichage de tous les Docteurs");
        return doctorRepository.findAll().stream()
                .map(doctorMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorResponseDTO getDoctorById(Long id) {
        log.info("Appel Affichage du Docteur avec ID {}", id);
        return doctorMapper.toResponse(doctorRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ErrorType.RESOURCE_NOT_FOUND,
                        "Le Docteur avec ID " + id + " est introuvable.")));
    }

    @Transactional
    @Override
    public DoctorResponseDTO updateDoctor(Long id, DoctorUpdateRequestDTO dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ErrorType.RESOURCE_NOT_FOUND,
                        "Le Docteur avec ID " + id + " que vous essayez de modifier n'existe pas !"));

        doctorMapper.updateDoctorFromDto(dto, doctor);
        log.info("Docteur ID {} mis à jour avec succès en mémoire", id);

        return doctorMapper.toResponse(doctor);
    }

    @Override
    @Transactional
    public void deleteDoctor(Long id) {
        log.info("Delete du Docteur avec ID {}", id);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ErrorType.RESOURCE_NOT_FOUND,
                        "Le Docteur avec ID " + id + " que vous essayez de supprimer n'existe pas !"));
        doctorRepository.deleteById(id);
    }

    @Transactional
    @Override
    public DisponibiliteResponseDTO addDisponibilite(Long doctorId, DisponibiliteRequestDTO dto) {
        validator.validateHoraires(dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime(), doctorId, null);

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ValidationException(ErrorType.RESOURCE_NOT_FOUND,
                        "Le Docteur avec ID " + doctorId + " n'existe pas."));

        Disponibilite disponibilite = disponibiliteMapper.requestDtoToEntity(dto);
        doctor.addDisponibilite(disponibilite);

        doctorRepository.save(doctor);

        return disponibiliteMapper.ToResponse(disponibilite);
    }

    @Override
    public List<DoctorResponseDTO> getDoctorsBySpecialty(String specialty) {
        log.info("Recherche des docteurs par spécialité : {}", specialty);
        return doctorRepository.findBySpecialty(specialty).stream()
                .map(doctorMapper::toResponse)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<DoctorResponseDTO> searchDoctors(String keyword) {
//        log.info("Recherche de docteurs avec le mot-clé : {}", keyword);
//        return doctorRepository.searchByKeyword(keyword).stream()
//                .map(doctorMapper::toResponse)
//                .collect(Collectors.toList());
//    }

    @Override
    public DoctorResponseDTO getDoctorByEmail(String email) {
        log.info("Recherche du docteur avec l'email : {}", email);
        return doctorRepository.findByEmail(email)
                .map(doctorMapper::toResponse)
                .orElseThrow(() -> new ValidationException(ErrorType.RESOURCE_NOT_FOUND,
                        "Aucun docteur trouvé avec l'email : " + email));
    }

    @Override
    public boolean existsById(Long id) {
        log.info("Vérification de l'existence du docteur ID : {}", id);
        return doctorRepository.existsById(id);
    }
}
