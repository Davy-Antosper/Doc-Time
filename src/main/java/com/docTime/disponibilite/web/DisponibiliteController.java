package com.docTime.disponibilite.web;

import com.docTime.apiResponse.ApiResponse;
import com.docTime.disponibilite.dto.DisponibiliteRequestDTO;
import com.docTime.disponibilite.dto.DisponibiliteResponseDTO;
import com.docTime.disponibilite.dto.DisponibiliteUpdateRequestDTO;
import com.docTime.disponibilite.service.DisponibiliteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/availabilities")
public class DisponibiliteController {

    private final DisponibiliteService disponibiliteService;

    public DisponibiliteController(DisponibiliteService disponibiliteService) {
        this.disponibiliteService = disponibiliteService;
    }

    @PostMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<DisponibiliteResponseDTO>> addAvailability(
            @PathVariable Long doctorId,
            @RequestBody DisponibiliteRequestDTO dto) {

        DisponibiliteResponseDTO apiResponse = disponibiliteService.addDisponibilite(doctorId, dto);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Disponibilité ajoutée avec succès.", LocalDateTime.now(), apiResponse),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<ApiResponse<List<DisponibiliteResponseDTO>>> getDoctorAvailabilities(@PathVariable Long doctorId) {
        List<DisponibiliteResponseDTO> response = disponibiliteService.getAvailabilitiesByDoctor(doctorId);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Planning récupéré avec succès.", LocalDateTime.now(), response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DisponibiliteResponseDTO>> updateAvailability(
            @PathVariable Long id,
            @RequestBody DisponibiliteUpdateRequestDTO dto) {

        DisponibiliteResponseDTO response = disponibiliteService.updateDisponibilite(id, dto);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Disponibilité modifiée avec succès.", LocalDateTime.now(), response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAvailability(@PathVariable Long id) {
        disponibiliteService.deleteDisponibilite(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Disponibilité supprimée avec succès.", LocalDateTime.now(), null)
        );
    }
}
