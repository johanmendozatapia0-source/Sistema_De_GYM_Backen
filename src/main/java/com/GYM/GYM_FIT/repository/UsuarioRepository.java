package com.GYM.GYM_FIT.repository;
import com.GYM.GYM_FIT.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{ Optional<Usuario> findByEmail(String email); boolean existsByEmail(String email); }
