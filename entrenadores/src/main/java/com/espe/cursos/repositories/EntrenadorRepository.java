package com.espe.cursos.repositories;

import com.espe.cursos.models.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
    // Consulta personalizada para obtener los entrenadores de un jugador
    @Query("SELECT e FROM Entrenador e JOIN e.entrenadorJugadors ej WHERE ej.jugadorId = :jugadorId")
    List<Entrenador> findEntrenadoresByJugadorId(@Param("jugadorId") Long jugadorId);
}
