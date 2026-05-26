package com.docTime.doctor.web;

import com.docTime.apiResponse.ApiResponse;
import com.docTime.doctor.dto.DoctorRequestDTO;
import com.docTime.doctor.dto.DoctorResponseDTO;
import com.docTime.doctor.dto.DoctorUpdateRequestDTO;
import com.docTime.doctor.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> createDoctor(@RequestBody DoctorRequestDTO doctor) {
        DoctorResponseDTO apiResponse = doctorService.createDoctor(doctor);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Créé avec succès", LocalDateTime.now(), apiResponse),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getAllDoctors() {
        List<DoctorResponseDTO> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Liste de tous les docteurs", LocalDateTime.now(), doctors)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> getDoctorById(@PathVariable Long id) {
        DoctorResponseDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Docteur trouvé avec l'ID " + id, LocalDateTime.now(), doctor)
        );
    }

    @GetMapping("/search/email")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> getDoctorByEmail(@RequestParam String email) {
        DoctorResponseDTO doctor = doctorService.getDoctorByEmail(email);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Docteur trouvé avec l'email : " + email, LocalDateTime.now(), doctor)
        );
    }

    @GetMapping("/search/specialty")
    public ResponseEntity<ApiResponse<List<DoctorResponseDTO>>> getDoctorsBySpecialty(@RequestParam String specialty) {
        List<DoctorResponseDTO> doctors = doctorService.getDoctorsBySpecialty(specialty);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Liste des docteurs spécialisés en " + specialty, LocalDateTime.now(), doctors)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponseDTO>> updateDoctor(@PathVariable Long id, @RequestBody DoctorUpdateRequestDTO dto) {
        DoctorResponseDTO updated = doctorService.updateDoctor(id, dto);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Modification effectuée avec succès", LocalDateTime.now(), updated)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Le docteur a été supprimé", LocalDateTime.now(), null)
        );
    }
}
