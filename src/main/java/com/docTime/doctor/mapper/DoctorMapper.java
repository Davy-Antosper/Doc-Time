package com.docTime.doctor.mapper;

import com.docTime.doctor.dto.DoctorRequestDTO;
import com.docTime.doctor.dto.DoctorResponseDTO;
import com.docTime.doctor.dto.DoctorUpdateRequestDTO;
import com.docTime.doctor.model.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" ,nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface DoctorMapper {
     Doctor doctorRequestToEntity(DoctorRequestDTO dto) ;
    DoctorResponseDTO toResponse(Doctor doctor);
    void updateDoctorFromDto(DoctorUpdateRequestDTO dto, @MappingTarget Doctor doctor);

}
