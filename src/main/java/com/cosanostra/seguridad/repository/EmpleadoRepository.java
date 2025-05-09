package com.cosanostra.seguridad.repository;
import com.cosanostra.seguridad.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{
}
