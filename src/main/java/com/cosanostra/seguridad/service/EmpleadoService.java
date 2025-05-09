package com.cosanostra.seguridad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosanostra.seguridad.model.Empleado;
import com.cosanostra.seguridad.repository.EmpleadoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Empleado> findAll(){
        return empleadoRepository.findAll();
    }
    public Empleado findById(long id){
        return empleadoRepository.findById(id).get();
    }
    public Empleado save(Empleado empleado){
        return empleadoRepository.save(empleado);
    }
    public void delate(long id){
        empleadoRepository.deleteById(id);
    }
}
