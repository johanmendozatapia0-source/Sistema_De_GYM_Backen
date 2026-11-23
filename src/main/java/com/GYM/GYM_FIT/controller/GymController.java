package com.GYM.GYM_FIT.controller;
import com.GYM.GYM_FIT.dto.*;
import com.GYM.GYM_FIT.service.GymService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api")
public class GymController {
    private final GymService service;
    public GymController(GymService service) { this.service=service; }
    @GetMapping("/membresias") public List<MembresiaResponse> membresias() { return service.membresias(); }
    @PostMapping("/membresias") @ResponseStatus(HttpStatus.CREATED)
    public MembresiaResponse crearMembresia(@Valid @RequestBody CrearMembresiaRequest r) { return service.crearMembresia(r); }
    @GetMapping("/pagos") public List<PagoResponse> pagos() { return service.pagos(); }
    @PostMapping("/pagos") @ResponseStatus(HttpStatus.CREATED)
    public PagoResponse crearPago(@Valid @RequestBody CrearPagoRequest r) { return service.crearPago(r); }
    @PostMapping("/acceso/validar") public AccesoResponse validar(@Valid @RequestBody ValidarAccesoRequest r) { return service.validarAcceso(r); }
    @GetMapping("/dashboard/resumen") public GymService.ResumenResponse resumen() { return service.resumen(); }
}
