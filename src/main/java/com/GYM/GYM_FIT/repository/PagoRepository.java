package com.GYM.GYM_FIT.repository;
import com.GYM.GYM_FIT.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public interface PagoRepository extends JpaRepository<Pago, Long> {
    @Query("select coalesce(sum(p.monto), 0) from Pago p where p.estado = :estado and p.fechaPago >= :desde and p.fechaPago < :hasta")
    BigDecimal sumarPagos(@Param("estado") EstadoPago estado, @Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
}
