package com.Backend.apiOdontologos.Service;

import com.Backend.apiOdontologos.Exceptions.ResourceNotFoundException;
import com.Backend.apiOdontologos.Model.Paciente;
import com.Backend.apiOdontologos.Repository.PacienteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    private PacienteRepository pacienteRepository;

    Logger LOGGER = Logger.getLogger(PacienteService.class);

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente guardarPaciente(Paciente paciente){
        LOGGER.info("Se ha guardado un paciente");
        return pacienteRepository.save(paciente);
    }
    public Optional<Paciente> buscarPaciente(Long id) throws ResourceNotFoundException {
        LOGGER.info("Iniciando la búsqueda del paciente con id="+id);
        Optional<Paciente> pacienteABuscar = pacienteRepository.findById(id);
        if(pacienteABuscar.isPresent()){
           return pacienteABuscar;}
        else {
            throw new ResourceNotFoundException("Error. No existe el ID= "+id+" asociado a un paciente en la base de datos.");
        }
    }
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        LOGGER.warn("Se ha eliminado el paciente con id="+id);
        Optional<Paciente> pacienteAEliminar = buscarPaciente(id);
        if(pacienteAEliminar.isPresent()){
        pacienteRepository.deleteById(id);}
        else {
            throw new ResourceNotFoundException("Error. No existe el ID= "+id+" asociado a un paciente en la base de datos.");
        }
    }
    public void actualizarPaciente(Paciente paciente) throws ResourceNotFoundException {
        LOGGER.info("Iniciando la actualización del paciente con id="+paciente.getId());
        Optional<Paciente> pacienteABuscar = buscarPaciente(paciente.getId());
        if(pacienteABuscar.isPresent()){
            pacienteRepository.save(paciente);
        } else {
            throw new ResourceNotFoundException("Error. No existe el ID= "+paciente.getId()+" asociado a un paciente en la base de datos.");
        }

    }
    public List<Paciente> buscarPacientes(){
        LOGGER.info("Mostrando los pacientes registrados");
        return pacienteRepository.findAll();
    }
    public Optional<Paciente> buscarPacientePorCorreo(String correo) throws ResourceNotFoundException {
        LOGGER.info("Se ha buscado un paciente con correo: " +  correo);
        Optional<Paciente> pacienteABuscar = pacienteRepository.findByEmail(correo);
        if(pacienteABuscar.isPresent()){
            return pacienteABuscar;
        }else{
            throw new ResourceNotFoundException("Error. No existe el paciente con correo "+ correo +" en la base de datos.");
        }

    }



}
