package com.docTime.appointment.model;

import lombok.Getter;

@Getter
public enum AppointmentStatus {

    PLANIFIE("STATUS_01", "Rendez-vous planifié (En attente de validation)"),
    CONFIRME("STATUS_02", "Rendez-vous confirmé (Créneau bloqué)"),
    ANNULE("STATUS_03", "Rendez-vous annulé (Créneau libéré)"),
    HONORE("STATUS_04", "Consultation effectuée (Patient présent)");

    private final String code;
    private final String label;

    AppointmentStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

}
