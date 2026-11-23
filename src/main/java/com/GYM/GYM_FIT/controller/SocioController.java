package com.GYM.GYM_FIT.controller;
import com.GYM.GYM_FIT.dto.*;
import com.GYM.GYM_FIT.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/socios")
public class SocioController {
    private final SocioService service;
    public SocioController(SocioService service) { this.service = service; }
    @GetMapping public List<SocioResponse> listar() { return service.listar(); }
    @GetMapping("/{codigo}") public SocioResponse buscar(@PathVariable String codigo) { return service.buscar(codigo); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public SocioResponse crear(@Valid @RequestBody CrearSocioRequest request) { return service.crear(request); }
}
