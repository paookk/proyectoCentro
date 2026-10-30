package com.caoc.centro_adopcion.repository;

import com.caoc.centro_adopcion.model.Adopcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Long> {
    // Aquí heredamos métodos automáticos como .save() para registrar las adopciones en MySQL
}