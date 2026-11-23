package com.GYM.GYM_FIT.service;
import com.GYM.GYM_FIT.dto.*;
import com.GYM.GYM_FIT.entity.Socio;
import com.GYM.GYM_FIT.repository.SocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
@Service
public class SocioService {
    private final SocioRepository socios;
    public SocioService(SocioRepository socios) { this.socios = socios; }
    @Transactional(readOnly = true)
    public List<SocioResponse> listar() { return socios.findAll().stream().map(SocioResponse::from).toList(); }
    @Transactional(readOnly = true)
    public SocioResponse buscar(String codigo) {
        return SocioResponse.from(socios.findByCodigoSocio(codigo).orElseThrow(() -> new RecursoNoEncontradoException("Socio no encontrado")));
    }
    @Transactional
    public SocioResponse crear(CrearSocioRequest request) {
        Socio socio = new Socio();
        socio.setNombres(request.nombres().trim()); socio.setDni(request.dni());
        socio.setTelefono(request.telefono()); socio.setFotoUrl(request.fotoUrl());
        String codigo;
        do { codigo = "GF" + ThreadLocalRandom.current().nextInt(100000, 1000000); }
        while (socios.existsByCodigoSocio(codigo));
        socio.setCodigoSocio(codigo);
        return SocioResponse.from(socios.save(socio));
    }
}
