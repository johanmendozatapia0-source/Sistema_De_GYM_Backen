package com.GYM.GYM_FIT.dto;
import com.GYM.GYM_FIT.entity.Entrenador;
public record EntrenadorResponse(Long id,String nombres,String especialidad,String telefono,boolean activo){
    public static EntrenadorResponse from(Entrenador e){return new EntrenadorResponse(e.getId(),e.getNombres(),e.getEspecialidad(),e.getTelefono(),e.isActivo());}
}
