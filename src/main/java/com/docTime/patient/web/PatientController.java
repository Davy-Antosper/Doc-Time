package com.docTime.patient.web;

import com.docTime.apiResponse.ApiResponse;
import com.docTime.patient.dto.PatientRequestDTO;
import com.docTime.patient.dto.PatientResponseDTO;
import com.docTime.patient.dto.PatientUpdateDTO;
import com.docTime.patient.service.PatientService;
import com.docTime.patient.service.PatientServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientServiceImpl patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<PatientResponseDTO>>> toutLesPatients(){
        ApiResponse<List<PatientResponseDTO>> apiResponse =
                new ApiResponse<>(true,"Liste de Tout les Patients.", LocalDateTime.now(),patientService.getAllPatients());
        return  ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> PatientById(@PathVariable Long id){
        ApiResponse<PatientResponseDTO> apiResponse =
                new ApiResponse<>(true,"Patient avec ID "+id, LocalDateTime.now(),patientService.getPatientById(id));
        return  ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> PatientByEmail(@RequestParam String email){
        ApiResponse<PatientResponseDTO> apiResponse =
                new ApiResponse<>(true,"Patient avec cette email ", LocalDateTime.now(),patientService.getPatientParEmail(email));
        return  ResponseEntity.ok(apiResponse);
    }

//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse<PatientResponseDTO>> PatientName(@RequestParam String fname,){
//        ApiResponse<PatientResponseDTO> apiResponse =
//                new ApiResponse<>(true,"Patient avec email "+email, LocalDateTime.now(),patientService.getPatientParEmail(email));
//        return  ResponseEntity.ok(apiResponse);
//    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<Page<PatientResponseDTO>>> getPatients(
//            @PageableDefault(
//                    page = 0,
//                    size = 20,
//                    sort = { "lastName", "firstName" },
//                    direction = Sort.Direction.ASC
//            ) Pageable pageable) {
//
//        Page<PatientResponseDTO> result = patientService.getAllPatients(pageable);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Succès",LocalDateTime.now(),result ));
//    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> create(@RequestBody PatientRequestDTO dto) {
        PatientResponseDTO apiResponse = patientService.createPatient(dto);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Créé avec succès", LocalDateTime.now(),apiResponse),
                HttpStatus.CREATED
        );
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> update(@PathVariable Long id,@RequestBody PatientUpdateDTO patientUpdateDTO){
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id,patientUpdateDTO);
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Modification avec succès", LocalDateTime.now(),patientResponseDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Le patient a été supprimé", LocalDateTime.now(),  patientService.deletePatient(id))
        );
    }
}
