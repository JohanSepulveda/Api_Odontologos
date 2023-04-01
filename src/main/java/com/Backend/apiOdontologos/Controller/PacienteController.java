package com.Backend.apiOdontologos.Controller;

import com.Backend.apiOdontologos.Exceptions.ResourceNotFoundException;
import com.Backend.apiOdontologos.Model.Paciente;
import com.Backend.apiOdontologos.Service.PacienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")

public class PacienteController {
    private PacienteService pacienteService;

    @Autowired

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscarPaciente(id).get());
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodosLosPacientes(){
        return ResponseEntity.ok(pacienteService.buscarPacientes());
    }

    @GetMapping("/buscar/correo/{email}")
    public ResponseEntity<Paciente> buscarPacientePorCorreo(@PathVariable String correo) throws ResourceNotFoundException {
            return ResponseEntity.ok(pacienteService.buscarPacientePorCorreo(correo).get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paciente registrarPaciente(@RequestBody Paciente paciente){
            return pacienteService.guardarPaciente(paciente);
    }

    @PutMapping
    public ResponseEntity<String>  actualizarPaciente(@RequestBody Paciente paciente) throws ResourceNotFoundException {
            pacienteService.actualizarPaciente(paciente);
            return  ResponseEntity.ok( "Se actualizó al paciente con id: " + paciente.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("Se eliminó al paciente con id: " + id);
    }

    }




