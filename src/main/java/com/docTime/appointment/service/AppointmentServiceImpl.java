package com.docTime.appointment.service;

import com.docTime.appointment.dto.AppointmentResponseDTO;
import com.docTime.appointment.model.Appointment;
import com.docTime.appointment.repository.AppointmentRepository;
import com.docTime.doctor.dto.DoctorResponseDTO;
import com.docTime.doctor.model.Doctor;
import com.docTime.doctor.service.DoctorService;
import com.docTime.patient.dto.PatientResponseDTO;
import com.docTime.patient.model.Patient;
import com.docTime.patient.service.PatientService;
import com.docTime.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final Validator validator;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientService patientService, DoctorService doctorService, Validator validator) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.validator = validator;
    }

    @Override
    public AppointmentResponseDTO createAppointment(Long patientId, Long doctorId, LocalDateTime dateTime, String notes) {
        PatientResponseDTO patient = patientService.findById(patientId);

        DoctorResponseDTO doctor = doctorService.findById(doctorId);

        validator.validateDoctorAvailability(doctor, dateTime);

        appointmentRepository.findByDoctorIdAndAppointmentDateTime(doctorId, dateTime)
                .ifPresent(app -> {
                    throw new RuntimeException("Ce créneau est déjà réservé pour le docteur " + doctor.getLastName());
                });

        Appointment appointment = new Appointment(patient, doctor, dateTime, notes);
        return appointmentRepository.save(appointment);
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
