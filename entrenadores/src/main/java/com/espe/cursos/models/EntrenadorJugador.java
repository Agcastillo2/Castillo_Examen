package com.espe.cursos.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "entrenador_jugador")
@Data
public class EntrenadorJugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jugadorId;
    private String jugadorNombre;
    private String jugadorApellido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(Long jugadorId) {
        this.jugadorId = jugadorId;
    }

    public String getJugadorApellido() {
        return jugadorApellido;
    }

    public void setJugadorApellido(String jugadorApellido) {
        this.jugadorApellido = jugadorApellido;
    }

    public String getJugadorNombre() {
        return jugadorNombre;
    }

    public void setJugadorNombre(String jugadorNombre) {
        this.jugadorNombre = jugadorNombre;
    }
}
