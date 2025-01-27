package com.espe.usuarios.repositories;

import com.espe.usuarios.models.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    boolean existsByNombreAndApellido(String nombre, String apellido);
}
