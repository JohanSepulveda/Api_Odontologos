package com.Backend.apiOdontologos.Controller;

import com.Backend.apiOdontologos.DTO.TurnoDto;
import com.Backend.apiOdontologos.Exceptions.ResourceNotFoundException;
import com.Backend.apiOdontologos.Service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private TurnoService turnoService;


    @Autowired
    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }
    @GetMapping
    public ResponseEntity<List<TurnoDto>> buscarTurnos(){
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<TurnoDto> buscarTurno(@PathVariable Long id) throws ResourceNotFoundException {
      return ResponseEntity.ok(turnoService.buscarTurno(id).get());
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TurnoDto registrarTurno(@RequestBody TurnoDto turno) throws ResourceNotFoundException {
            return turnoService.guardarTurno(turno);
    }
    @PutMapping
    public ResponseEntity<String> modificarTurno(@RequestBody TurnoDto turno) throws ResourceNotFoundException {
            turnoService.actualizarTurno(turno);
            return ResponseEntity.ok("Turno actualizado");
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado");}

}
