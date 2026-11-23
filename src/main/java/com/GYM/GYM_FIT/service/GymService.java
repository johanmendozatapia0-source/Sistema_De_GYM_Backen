package com.GYM.GYM_FIT.service;
import com.GYM.GYM_FIT.dto.*;
import com.GYM.GYM_FIT.entity.*;
import com.GYM.GYM_FIT.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.math.BigDecimal;
import java.util.List;
@Service
public class GymService {
    private final MembresiaRepository membresias;
    private final SocioRepository socios;
    private final PagoRepository pagos;
    private final AsistenciaRepository asistencias;
    public GymService(MembresiaRepository m, SocioRepository s, PagoRepository p, AsistenciaRepository a) { membresias=m; socios=s; pagos=p; asistencias=a; }
    @Transactional(readOnly=true)
    public List<MembresiaResponse> membresias() { return membresias.findAll().stream().map(MembresiaResponse::from).toList(); }
    @Transactional
    public MembresiaResponse crearMembresia(CrearMembresiaRequest r) {
        Membresia m=new Membresia(); m.setNombre(r.nombre().trim()); m.setDuracionDias(r.duracionDias()); m.setPrecio(r.precio());
        return MembresiaResponse.from(membresias.save(m));
    }
    @Transactional
    public PagoResponse crearPago(CrearPagoRequest r) {
        Socio s=socios.findById(r.socioId()).orElseThrow(()->new RecursoNoEncontradoException("Socio no encontrado"));
        Membresia m=membresias.findById(r.membresiaId()).orElseThrow(()->new RecursoNoEncontradoException("Membresía no encontrada"));
        if(!m.isActiva()) throw new IllegalArgumentException("La membresía está inactiva");
        Pago p=new Pago(); p.setSocio(s); p.setMembresia(m); p.setMonto(m.getPrecio()); p.setEstado(EstadoPago.PAGADO);
        s.setMembresia(m); LocalDate start=s.getMembresiaHasta()!=null && s.getMembresiaHasta().isAfter(LocalDate.now()) ? s.getMembresiaHasta() : LocalDate.now();
        s.setMembresiaHasta(start.plusDays(m.getDuracionDias())); socios.save(s);
        return PagoResponse.from(pagos.save(p));
    }
    @Transactional(readOnly=true)
    public List<PagoResponse> pagos() { return pagos.findAll().stream().map(PagoResponse::from).toList(); }
    @Transactional
    public AccesoResponse validarAcceso(ValidarAccesoRequest r) {
        Socio s=socios.findByCodigoSocio(r.codigoSocio()).orElseThrow(()->new RecursoNoEncontradoException("Socio no encontrado"));
        boolean permit=s.isActivo() && s.getMembresiaHasta()!=null && !s.getMembresiaHasta().isBefore(LocalDate.now());
        String motivo=permit ? "Membresía vigente" : !s.isActivo() ? "Socio inactivo" : "Membresía vencida o no registrada";
        Asistencia a=new Asistencia(); a.setSocio(s); a.setAccesoPermitido(permit); a.setMotivo(motivo); asistencias.save(a);
        return new AccesoResponse(s.getCodigoSocio(),s.getNombres(),permit,motivo,a.getFechaHoraEntrada());
    }
    @Transactional(readOnly=true)
    public ResumenResponse resumen() {
        LocalDate hoy=LocalDate.now(); LocalDateTime desde=hoy.withDayOfMonth(1).atStartOfDay();
        BigDecimal ingresos=pagos.sumarPagos(EstadoPago.PAGADO,desde,hoy.plusMonths(1).withDayOfMonth(1).atStartOfDay());
        return new ResumenResponse(socios.countByActivoTrue(), ingresos, asistencias.countByAccesoPermitidoTrueAndFechaHoraEntradaAfter(LocalDateTime.now().minusDays(7)));
    }
    public record ResumenResponse(long sociosActivos, BigDecimal ingresosMes, long asistenciasSemana) {}
}
