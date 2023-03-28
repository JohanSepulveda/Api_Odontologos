package com.Backend.api_odontologos.Controller;

import com.Backend.api_odontologos.Exceptions.ResourceNotFoundException;
import com.Backend.api_odontologos.Model.Odontologo;
import com.Backend.api_odontologos.Service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private OdontologoService odontologoService;

    @Autowired

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo>buscarOdontologo(@PathVariable Long id){
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(id);
        if(odontologoBuscado.isPresent()){
        return ResponseEntity.ok(odontologoBuscado.get());
        }else{
          return   ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodosLosOdontologos(){
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }


    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }


    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo){
        // preguntar si existe el paciente
         Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologo(odontologo.getId());
        if(odontologoBuscado.isPresent()) {
             odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok( "Se actualizó al odontologo con id: " + odontologo.getId());
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok( "Se eliminó al odontologo con id: " +id);
    }



}
