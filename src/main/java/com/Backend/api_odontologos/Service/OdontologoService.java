package com.Backend.api_odontologos.Service;

import com.Backend.api_odontologos.Exceptions.ResourceNotFoundException;
import com.Backend.api_odontologos.Model.Odontologo;
import com.Backend.api_odontologos.Repository.OdontologoRepository;
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

    public Optional<Odontologo> buscarOdontologo(Long id){
        LOGGER.info("Iniciando la busqueda de un odontologo con id: " + id);
        return odontologoRepository.findById(id);}
    public void actualizarOdontologo(Odontologo odontologo){
        LOGGER.info("Iniciando la actualización del paciente con id=" + odontologo.getId());
        odontologoRepository.save(odontologo); }
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
