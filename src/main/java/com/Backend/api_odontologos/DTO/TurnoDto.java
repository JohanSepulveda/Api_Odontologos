package com.Backend.api_odontologos.DTO;

import java.time.LocalDate;

public class TurnoDto {
    private Long id;

    private Long odontologoId;

    private Long pacienteId;

    private LocalDate fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOdontologoId() {
        return odontologoId;
    }

    public void setOdontologoId(Long odontologoId) {
        this.odontologoId = odontologoId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
