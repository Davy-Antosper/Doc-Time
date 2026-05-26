package com.docTime.appointment.service;

import com.docTime.appointment.dto.AppointmentResponseDTO;
import com.docTime.appointment.repository.AppointmentRepository;
import com.docTime.doctor.model.Doctor;
import com.docTime.patient.model.Patient;
import com.docTime.patient.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientService patientService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
    }

    @Override
    public AppointmentResponseDTO createAppointment(Long patientId, Long doctorId, LocalDateTime dateTime, String notes) {
        // 1. Le patient existe-t-il ?
        Patient patient = patientService.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID : " + patientId));

        // 2. Le docteur existe-t-il ?
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Docteur non trouvé avec l'ID : " + doctorId));

        // 3. Le docteur est-il disponible ce jour-là à cette heure-là ?
        validateDoctorAvailability(doctor, dateTime);

        // 4. Le docteur n'a-t-il pas déjà un rendez-vous à la même date/heure ?
        appointmentRepository.findByDoctorIdAndAppointmentDateTime(doctorId, dateTime)
                .ifPresent(app -> {
                    throw new RuntimeException("Ce créneau est déjà réservé pour le docteur " + doctor.getLastName());
                });

        // Tout est bon, on crée le rendez-vous
        Appointment appointment = new Appointment(patient, doctor, dateTime, notes);
        return appointmentRepository.save(appointment);
    }

    @Override
    public void validateDoctorAvailability(Doctor doctor, LocalDateTime dateTime) {

    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments() {
        return List.of();
    }

    @Override
    public Optional<AppointmentResponseDTO> getAppointmentById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByPatient(Long patientId) {
        return List.of();
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(Long doctorId) {
        return List.of();
    }

    @Override
    public AppointmentResponseDTO cancelAppointment(Long id) {
        return null;
    }

    @Override
    public void deleteAppointment(Long id) {

    }
}
