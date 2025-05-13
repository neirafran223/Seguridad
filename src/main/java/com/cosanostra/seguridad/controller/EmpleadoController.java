package com.cosanostra.seguridad.controller;

import java.util.List;

import com.cosanostra.seguridad.model.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosanostra.seguridad.service.EmpleadoService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5501")
@RequestMapping("/api/v1/empleados")
public class EmpleadoController{

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<Empleado>> Listar(){
        List<Empleado> empleado = empleadoService.findAll();
        if (empleado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(empleado);
    }
    @PostMapping
    public ResponseEntity<Empleado> guardar(@RequestBody Empleado empleado){
        Empleado productonuevo = empleadoService.save(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(productonuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscar(@PathVariable Integer id){
        try{
            Empleado empleado = empleadoService.findById(id);
            return ResponseEntity.ok(empleado);
        } catch( Exception  e ) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Integer id, @RequestBody Empleado empleado){
        try{
            Empleado emp = empleadoService.findById(id);
            emp.setId(id);
            emp.setNombreEmpresa(empleado.getNombreEmpresa());
            emp.setCantPersonal(empleado.getCantPersonal());
            emp.setTipoSeguridad(empleado.getTipoSeguridad());

            empleadoService.save(emp);
            return ResponseEntity.ok(empleado);
        } catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            empleadoService.delate(id);
            return ResponseEntity.noContent().build();
        } catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }
}
