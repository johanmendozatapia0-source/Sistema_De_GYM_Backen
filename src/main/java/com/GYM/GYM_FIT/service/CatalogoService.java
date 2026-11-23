package com.GYM.GYM_FIT.service;
import com.GYM.GYM_FIT.dto.*;
import com.GYM.GYM_FIT.entity.*;
import com.GYM.GYM_FIT.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class CatalogoService {
    private final EntrenadorRepository entrenadores;private final RutinaRepository rutinas;
    public CatalogoService(EntrenadorRepository e,RutinaRepository r){entrenadores=e;rutinas=r;}
    @Transactional(readOnly=true) public List<EntrenadorResponse> entrenadores(){return entrenadores.findAll().stream().map(EntrenadorResponse::from).toList();}
    @Transactional public EntrenadorResponse crearEntrenador(CrearEntrenadorRequest r){Entrenador e=new Entrenador();e.setNombres(r.nombres().trim());e.setEspecialidad(r.especialidad());e.setTelefono(r.telefono());return EntrenadorResponse.from(entrenadores.save(e));}
    @Transactional(readOnly=true) public List<RutinaResponse> rutinas(){return rutinas.findAll().stream().map(RutinaResponse::from).toList();}
    @Transactional public RutinaResponse crearRutina(CrearRutinaRequest r){Rutina rutina=new Rutina();rutina.setTitulo(r.titulo().trim());rutina.setDuracionSemanas(r.duracionSemanas());rutina.setNivel(r.nivel());rutina.setImagenUrl(r.imagenUrl());if(r.entrenadorId()!=null)rutina.setEntrenador(entrenadores.findById(r.entrenadorId()).orElseThrow(()->new RecursoNoEncontradoException("Entrenador no encontrado")));return RutinaResponse.from(rutinas.save(rutina));}
}
