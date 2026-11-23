package com.GYM.GYM_FIT.repository;
import com.GYM.GYM_FIT.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    long countByAccesoPermitidoTrueAndFechaHoraEntradaAfter(LocalDateTime desde);
}
