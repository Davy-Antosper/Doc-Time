package com.docTime.appointment.service;

import com.docTime.appointment.dto.AppointmentResponseDTO;
import com.docTime.appointment.model.Appointment;
import com.docTime.doctor.model.Doctor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    AppointmentResponseDTO createAppointment(Long patientId, Long doctorId, LocalDateTime dateTime, String notes);
    void validateDoctorAvailability(Doctor doctor, LocalDateTime dateTime);
    List<AppointmentResponseDTO> getAllAppointments();
    Optional<AppointmentResponseDTO> getAppointmentById(Long id);
    List<AppointmentResponseDTO> getAppointmentsByPatient(Long patientId);
    List<AppointmentResponseDTO> getAppointmentsByDoctor(Long doctorId);
    AppointmentResponseDTO cancelAppointment(Long id);
    void deleteAppointment(Long id);

}
