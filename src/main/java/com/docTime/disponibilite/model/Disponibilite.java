    package com.docTime.disponibilite.model;

    import com.docTime.doctor.model.Doctor;
    import com.fasterxml.jackson.annotation.JsonBackReference;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.time.DayOfWeek;
    import java.time.LocalTime;

    @Entity
    @NoArgsConstructor
    @Getter
    @Setter
    @Table(name = "disponibilites")
    public class Disponibilite {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        private DayOfWeek dayOfWeek;

        private LocalTime startTime;
        private LocalTime endTime;

        @ManyToOne
        @JoinColumn(name = "doctor_id")
        @JsonBackReference
        private Doctor doctor;

        public Disponibilite(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
        }

    }