package com.Backend.api_odontologos.Controller;

import com.Backend.api_odontologos.Exceptions.ResourceNotFoundException;
import com.Backend.api_odontologos.Model.Paciente;
import com.Backend.api_odontologos.Service.PacienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")

public class PacienteController {
    private PacienteService pacienteService;

    @Autowired

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPaciente(@PathVariable Long id){
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(id);
        if(pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        } else {
            return  ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodosLosPacientes(){
        return ResponseEntity.ok(pacienteService.buscarPacientes());
    }

    @GetMapping("/buscar/correo/{email}")
    public ResponseEntity<Paciente> buscarPacientePorCorreo(@PathVariable String email){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPacientePorCorreo(email);
        if (pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente){
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPacientePorCorreo(paciente.getEmail());
        if (pacienteBuscado.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        else{
            return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
        }
    }

    @PutMapping
    public ResponseEntity<String>  actualizarPaciente(@RequestBody Paciente paciente){
        // preguntar si existe el paciente
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPaciente(paciente.getId());
        if(pacienteBuscado.isPresent()) {
            pacienteService.actualizarPaciente(paciente);
            return  ResponseEntity.ok( "Se actualizó al paciente con id: " + paciente.getId());
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {

            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("Se eliminó al paciente con id: " + id);

        }

    }




