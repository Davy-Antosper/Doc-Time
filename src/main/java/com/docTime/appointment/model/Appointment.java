package com.docTime.appointment.model;

import com.docTime.doctor.model.Doctor;
import com.docTime.patient.model.Patient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private LocalDateTime appointmentDateTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.PLANIFIE;

    private String notes;

    public Appointment() {}

    public Appointment(Patient patient, Doctor doctor, LocalDateTime appointmentDateTime, String notes) {
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDateTime = appointmentDateTime;
        this.notes = notes;
        this.status = AppointmentStatus.PLANIFIE;
    }

}