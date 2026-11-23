package com.GYM.GYM_FIT.repository;
import com.GYM.GYM_FIT.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface SocioRepository extends JpaRepository<Socio, Long> {
    Optional<Socio> findByCodigoSocio(String codigoSocio);
    boolean existsByCodigoSocio(String codigoSocio);
    long countByActivoTrue();
}
