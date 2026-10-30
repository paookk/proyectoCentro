package com.caoc.centro_adopcion.repository;

import com.caoc.centro_adopcion.model.Adoptante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptanteRepository extends JpaRepository<Adoptante, Long> {
    // metodo personalizado para buscar por nombre y contraseña
    Adoptante findByNombreAndPassword(String nombre, String password);
}