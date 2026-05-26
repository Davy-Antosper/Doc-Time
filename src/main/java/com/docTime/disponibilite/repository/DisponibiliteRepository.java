package com.docTime.disponibilite.repository;

import com.docTime.disponibilite.model.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Long> {

    List<Disponibilite> findByDoctorId(Long doctorId);

    // Requête pour détecter les chevauchements d'horaires pour un même médecin et un même jour
    @Query("SELECT COUNT(d) > 0 FROM Disponibilite d WHERE d.doctor.id = :doctorId " +
            "AND d.dayOfWeek = :dayOfWeek " +
            "AND (:id IS NULL OR d.id != :id) " +
            "AND (:startTime < d.endTime AND :endTime > d.startTime)")
    boolean hasOverlap(@Param("doctorId") Long doctorId,
                       @Param("dayOfWeek") DayOfWeek dayOfWeek,
                       @Param("startTime") LocalTime startTime,
                       @Param("endTime") LocalTime endTime,
                       @Param("id") Long id);
}
