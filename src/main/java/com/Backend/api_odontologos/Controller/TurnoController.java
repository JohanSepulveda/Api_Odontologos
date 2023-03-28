package com.Backend.api_odontologos.Controller;

import com.Backend.api_odontologos.DTO.TurnoDto;
import com.Backend.api_odontologos.Exceptions.ResourceNotFoundException;
import com.Backend.api_odontologos.Service.OdontologoService;
import com.Backend.api_odontologos.Service.PacienteService;
import com.Backend.api_odontologos.Service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private TurnoService turnoService;
    private PacienteService pacienteService;

    private OdontologoService odontologoService;

    @Autowired
    public TurnoController(TurnoService turnoService, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoService = turnoService;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }



    @GetMapping
    public ResponseEntity<List<TurnoDto>> buscarTurnos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<TurnoDto> buscarTurno(@PathVariable Long id){
       Optional<TurnoDto> turnoBuscado = turnoService.buscarTurno(id);
        if(turnoBuscado.isPresent()){
            return ResponseEntity.ok( turnoBuscado.get());
        } else{

            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<TurnoDto> registrarTurno(@RequestBody TurnoDto turno){
        //si el odontologo o paciente no existe error
        if(odontologoService.buscarOdontologo(turno.getOdontologoId()).isPresent()
                &&pacienteService.buscarPaciente(turno.getPacienteId()).isPresent()){
            //ambos existen, puedo guardar el turno
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping
    public ResponseEntity<String> modificarTurno(@RequestBody TurnoDto turno){
      Optional<TurnoDto> turnoBuscado = turnoService.buscarTurno(turno.getId());
    if(turnoBuscado.isPresent()){
        if(odontologoService.buscarOdontologo(turnoBuscado.get().getOdontologoId()).isPresent()
                &&pacienteService.buscarPaciente(turnoBuscado.get().getPacienteId()).isPresent()){
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("Turno actualizado");
        }
        else{
            return ResponseEntity.badRequest().build();
        }

    } else{
        return ResponseEntity.notFound().build();
    }
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado");}

}
