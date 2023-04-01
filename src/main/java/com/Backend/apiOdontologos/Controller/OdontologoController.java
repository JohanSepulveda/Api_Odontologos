package com.Backend.apiOdontologos.Controller;

import com.Backend.apiOdontologos.Exceptions.ResourceNotFoundException;
import com.Backend.apiOdontologos.Model.Odontologo;
import com.Backend.apiOdontologos.Service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private OdontologoService odontologoService;

    @Autowired

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo>buscarOdontologo(@PathVariable Long id) throws  ResourceNotFoundException{
        return   ResponseEntity.ok(odontologoService.buscarOdontologo(id).get());
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodosLosOdontologos(){
        return ResponseEntity.ok(odontologoService.listarOdontologos());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Odontologo registrarOdontologo(@RequestBody Odontologo odontologo){
        return odontologoService.guardarOdontologo(odontologo);
    }


    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
             odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok( "Se actualizó al odontologo con id: " + odontologo.getId());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok( "Se eliminó al odontologo con id: " +id);
    }



}
