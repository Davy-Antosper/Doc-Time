package com.docTime.mapper;

import com.docTime.dto.PatientRequestDTO;
import com.docTime.dto.PatientResponseDTO;
import com.docTime.dto.PatientUpdateDTO;
import com.docTime.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapper {
    Patient toEntity(PatientRequestDTO dto);
    PatientResponseDTO toDTO(Patient patient);
    PatientResponseDTO updateResponseDTOFromPatientUpdateDTO(PatientUpdateDTO patient,@MappingTarget PatientResponseDTO response);
    void updatePatientFromDto(PatientUpdateDTO dto, @MappingTarget Patient patient);
}
