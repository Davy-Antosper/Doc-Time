package com.docTime.disponibilite.service;

import com.docTime.disponibilite.dto.DisponibiliteRequestDTO;
import com.docTime.disponibilite.dto.DisponibiliteResponseDTO;
import com.docTime.disponibilite.dto.DisponibiliteUpdateRequestDTO;
import com.docTime.disponibilite.mapper.DisponibiliteMapper;
import com.docTime.disponibilite.model.Disponibilite;
import com.docTime.disponibilite.repository.DisponibiliteRepository;
import com.docTime.doctor.model.Doctor;
import com.docTime.doctor.repository.DoctorRepository;
import com.docTime.doctor.service.DoctorService;
import com.docTime.validation.ErrorType;
import com.docTime.validation.ValidationException;
import com.docTime.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class DisponibiliteServiceImpl implements DisponibiliteService {

    private final DoctorService doctorService;
    private final DisponibiliteRepository disponibiliteRepository;
    private final DisponibiliteMapper disponibiliteMapper;
    private final Validator validator;

    public DisponibiliteServiceImpl(DoctorService doctorService,
                                    DisponibiliteRepository disponibiliteRepository,
                                    DisponibiliteMapper disponibiliteMapper, Validator validator) {
        this.doctorService = doctorService;
        this.disponibiliteRepository = disponibiliteRepository;
        this.disponibiliteMapper = disponibiliteMapper;
        this.validator = validator;
    }

    @Transactional
    @Override
    public DisponibiliteResponseDTO addDisponibilite(Long doctorId, DisponibiliteRequestDTO dto) {
        return doctorService.addDisponibilite(doctorId, dto);
    }
    @Override
    public List<DisponibiliteResponseDTO> getAvailabilitiesByDoctor(Long doctorId) {
        log.info("Récupération du planning pour le Docteur ID {}", doctorId);
        if (!doctorService.existsById(doctorId)) {
            throw new ValidationException(ErrorType.RESOURCE_NOT_FOUND, "Le Docteur avec ID " + doctorId + " n'existe pas.");
        }
        return disponibiliteRepository.findByDoctorId(doctorId).stream()
                .map(disponibiliteMapper::ToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DisponibiliteResponseDTO updateDisponibilite(Long id, DisponibiliteUpdateRequestDTO dto) {
        log.info("Mise à jour de la disponibilité ID {}", id);

        Disponibilite disponibilite = disponibiliteRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ErrorType.RESOURCE_NOT_FOUND,
                        "La disponibilité avec ID " + id + " n'existe pas."));

        validator.validateHoraires(dto.getDayOfWeek(), dto.getStartTime(), dto.getEndTime(), disponibilite.getDoctor().getId(), id);

        disponibilite.setDayOfWeek(dto.getDayOfWeek());
        disponibilite.setStartTime(dto.getStartTime());
        disponibilite.setEndTime(dto.getEndTime());

        return disponibiliteMapper.ToResponse(disponibilite);
    }

    @Transactional
    @Override
    public void deleteDisponibilite(Long id) {
        log.info("Suppression de la disponibilité ID {}", id);
        Disponibilite disponibilite = disponibiliteRepository.findById(id)
                .orElseThrow(() -> new ValidationException(ErrorType.RESOURCE_NOT_FOUND,
                        "La disponibilité avec ID " + id + " n'existe pas."));

        disponibilite.getDoctor().removeDisponibilite(disponibilite);
        disponibiliteRepository.delete(disponibilite);
    }


}
