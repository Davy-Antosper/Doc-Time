package com.docTime.patient.mapper;

import com.docTime.patient.dto.PatientRequestDTO;
import com.docTime.patient.dto.PatientResponseDTO;
import com.docTime.patient.dto.PatientUpdateDTO;
import com.docTime.patient.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {
    Patient toEntity(PatientRequestDTO dto);

    PatientResponseDTO toDTO(Patient patient);

    void updatePatientFromDto(PatientUpdateDTO dto, @MappingTarget Patient patient);
}
