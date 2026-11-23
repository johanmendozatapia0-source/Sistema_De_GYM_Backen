package com.GYM.GYM_FIT.dto;
import com.GYM.GYM_FIT.entity.Rutina;
public record RutinaResponse(Long id,String titulo,int duracionSemanas,String nivel,String imagenUrl,Long entrenadorId,String entrenador){
    public static RutinaResponse from(Rutina r){return new RutinaResponse(r.getId(),r.getTitulo(),r.getDuracionSemanas(),r.getNivel(),r.getImagenUrl(),r.getEntrenador()==null?null:r.getEntrenador().getId(),r.getEntrenador()==null?null:r.getEntrenador().getNombres());}
}
