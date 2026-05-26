package com.docTime.disponibilite.service;

import com.docTime.disponibilite.dto.DisponibiliteRequestDTO;
import com.docTime.disponibilite.dto.DisponibiliteResponseDTO;
import com.docTime.disponibilite.dto.DisponibiliteUpdateRequestDTO;

import java.util.List;

public interface DisponibiliteService {
    DisponibiliteResponseDTO addDisponibilite(Long doctorId, DisponibiliteRequestDTO dto);
    List<DisponibiliteResponseDTO> getAvailabilitiesByDoctor(Long doctorId);
    DisponibiliteResponseDTO updateDisponibilite(Long id, DisponibiliteUpdateRequestDTO dto);
    void deleteDisponibilite(Long id);
}
