package com.docTime.doctor.model;

import com.docTime.disponibilite.model.Disponibilite;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "doctors")
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    @Column(length = 2)
    private String countryCode;
    private String specialty;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Disponibilite> availabilities = new ArrayList<>();

    public Doctor(String firstName, String lastName,String phone ,String email, String specialty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.specialty = specialty;
        this.phone=phone;
    }


    public void addDisponibilite(Disponibilite disponibilite) {
        availabilities.add(disponibilite);
        disponibilite.setDoctor(this);
    }

    public void removeDisponibilite(Disponibilite disponibilite) {
        availabilities.remove(disponibilite);
        disponibilite.setDoctor(null);
    }

}