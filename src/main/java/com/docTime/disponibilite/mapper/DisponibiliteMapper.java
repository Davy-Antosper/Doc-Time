package com.docTime.disponibilite.mapper;

import com.docTime.disponibilite.dto.DisponibiliteRequestDTO;
import com.docTime.disponibilite.dto.DisponibiliteResponseDTO;
import com.docTime.disponibilite.model.Disponibilite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" ,nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
public interface DisponibiliteMapper {

    Disponibilite requestDtoToEntity(DisponibiliteRequestDTO dto);

    @Mapping(source = "doctor.id", target = "doctorId")
    DisponibiliteResponseDTO ToResponse(Disponibilite disponibilite);
}
