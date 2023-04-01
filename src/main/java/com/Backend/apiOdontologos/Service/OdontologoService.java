package com.Backend.apiOdontologos.Service;

import com.Backend.apiOdontologos.Exceptions.ResourceNotFoundException;
import com.Backend.apiOdontologos.Model.Odontologo;
import com.Backend.apiOdontologos.Repository.OdontologoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class OdontologoService {

    private OdontologoRepository odontologoRepository;

    Logger LOGGER = Logger.getLogger(OdontologoService.class);

    @Autowired

    public OdontologoService(OdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        LOGGER.info("Se ha guardado un odontologo");
        return odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarOdontologo(Long id) throws ResourceNotFoundException{
        LOGGER.info("Iniciando la busqueda de un odontologo con id: " + id);
        Optional<Odontologo> odontologoABuscar = odontologoRepository.findById(id);
        if(odontologoABuscar.isPresent()){
            return odontologoABuscar;
        }else{
            throw new ResourceNotFoundException("El odontologo con id: " + id + " no existe.");
        }
       }
    public void actualizarOdontologo(Odontologo odontologo) throws ResourceNotFoundException{
        LOGGER.info("Iniciando la actualización del paciente con id=" + odontologo.getId());
        Optional<Odontologo> odontologoABuscar = odontologoRepository.findById(odontologo.getId());
        if(odontologoABuscar.isPresent()){
            odontologoRepository.save(odontologo);
        }else{
            throw new ResourceNotFoundException("El odontologo con id: " + odontologo.getId() + " no existe. Por tanto no se puede actualizar");
        }
    }
    public void eliminarOdontologo(Long id) throws ResourceNotFoundException {
        LOGGER.warn("Se ha eliminado el odontologo con id: " + id);
        Optional<Odontologo> odontologoAEliminar = buscarOdontologo(id);
        if(odontologoAEliminar.isPresent()){
        odontologoRepository.deleteById(id);}
        else {
            throw new ResourceNotFoundException("Error. No existe el ID= "+id+" asociado a un odontologo en la base de datos.");
        }
        }
    public List<Odontologo> listarOdontologos(){
        LOGGER.info("Mostrando los odontologos resgistrados");
        return odontologoRepository.findAll();
    }
}
