package com.GYM.GYM_FIT.controller;
import com.GYM.GYM_FIT.dto.*;
import com.GYM.GYM_FIT.service.CatalogoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api")
public class CatalogoController {
    private final CatalogoService service;
    public CatalogoController(CatalogoService s){service=s;}
    @GetMapping("/entrenadores") public List<EntrenadorResponse> entrenadores(){return service.entrenadores();}
    @PostMapping("/entrenadores") @ResponseStatus(HttpStatus.CREATED) public EntrenadorResponse crearEntrenador(@Valid @RequestBody CrearEntrenadorRequest r){return service.crearEntrenador(r);}
    @GetMapping("/rutinas") public List<RutinaResponse> rutinas(){return service.rutinas();}
    @PostMapping("/rutinas") @ResponseStatus(HttpStatus.CREATED) public RutinaResponse crearRutina(@Valid @RequestBody CrearRutinaRequest r){return service.crearRutina(r);}
}
