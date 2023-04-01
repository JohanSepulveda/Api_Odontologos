package com.Backend.apiOdontologos.Service;

import com.Backend.apiOdontologos.DTO.TurnoDto;
import com.Backend.apiOdontologos.Exceptions.ResourceNotFoundException;
import com.Backend.apiOdontologos.Model.Odontologo;
import com.Backend.apiOdontologos.Model.Paciente;
import com.Backend.apiOdontologos.Model.Turno;
import com.Backend.apiOdontologos.Repository.TurnoRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    private TurnoRepository turnoRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private OdontologoService odontologoService;

    Logger LOGGER = Logger.getLogger(TurnoService.class);

    @Autowired
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public List<TurnoDto> listarTurnos(){
        LOGGER.info("Mostrando los turnos resgistrados");
        List<Turno> turnosEncontrados = turnoRepository.findAll();
        // recorremos la lista para ir convirtiendo cada elemento
        List<TurnoDto> respuesta = new ArrayList<>();
        for (Turno turno : turnosEncontrados) {
            respuesta.add(turnoADto(turno));
        }
        return respuesta ;
    }

    public Optional<TurnoDto> buscarTurno(Long id) throws ResourceNotFoundException {
        LOGGER.info("Iniciando la búsqueda del turno con id="+id);
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        if(turnoBuscado.isPresent()){
            return Optional.of(turnoADto(turnoBuscado.get()));
        } else{
            throw new ResourceNotFoundException("Error. No existe el ID= "+id+" asociado a un turno en la base de datos.");
        }
    }
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        LOGGER.warn("Se ha eliminado el turno con id " + id);
        Optional<TurnoDto> turnoAEliminar = buscarTurno(id);
        if(turnoAEliminar.isPresent()){
        turnoRepository.deleteById(id);}
        else {
            throw new ResourceNotFoundException("Error. No existe el ID= "+id+" asociado a un turno en la base de datos.");
        }
    }
    public void actualizarTurno(TurnoDto turnoDto) throws ResourceNotFoundException {
        LOGGER.info("Se ha actualizado un turno");
        Optional<TurnoDto> turnoBuscado = buscarTurno(turnoDto.getId());
        if(turnoBuscado.isPresent()){
            if(odontologoService.buscarOdontologo(turnoBuscado.get().getOdontologoId()).isPresent()
                    &&pacienteService.buscarPaciente(turnoBuscado.get().getPacienteId()).isPresent()){
                     turnoRepository.save(dtoATurno(turnoDto));}
        } else {
            throw new ResourceNotFoundException("Error. No existe el ID= "+turnoDto.getId()+" asociado a un turno en la base de datos.");
        }
    }
    public TurnoDto guardarTurno(TurnoDto turnoDto) throws ResourceNotFoundException {
        LOGGER.info("Se ha guardado un turno");
        Turno turno = turnoRepository.save(dtoATurno(turnoDto));
        if(odontologoService.buscarOdontologo(turno.getOdontologo().getId()).isPresent()
                && pacienteService.buscarPaciente(turno.getPaciente().getId()).isPresent()){
        return turnoADto(turno);
        } else{
            throw new RuntimeException(".");
        }
    }


    //Transformación de a DTO
    private TurnoDto turnoADto(Turno turno){
        TurnoDto respuesta = new TurnoDto();
        respuesta.setId(turno.getId());
        respuesta.setOdontologoId(turno.getOdontologo().getId());
        respuesta.setPacienteId(turno.getPaciente().getId());
        respuesta.setFecha(turno.getFecha());
        return respuesta;
    }

    private Turno dtoATurno(TurnoDto turnoDto){
        Turno respuesta = new Turno();
        // cargar la información del DTO al Turno

        Odontologo odontologo = new Odontologo();
        odontologo.setId(turnoDto.getOdontologoId());

        Paciente paciente = new Paciente();
        paciente.setId(turnoDto.getPacienteId());

        respuesta.setId(turnoDto.getId());
        respuesta.setFecha(turnoDto.getFecha());
        respuesta.setOdontologo(odontologo);
        respuesta.setPaciente(paciente);

        return respuesta;
    }
}
