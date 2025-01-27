package com.espe.usuarios.services;

import com.espe.usuarios.models.Jugador;
import com.espe.usuarios.repositories.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    public List<Jugador> findAll() {
        return jugadorRepository.findAll();
    }

    public Optional<Jugador> findById(Long id) {
        return jugadorRepository.findById(id);
    }

    public Jugador save(Jugador jugador) {
        return jugadorRepository.save(jugador);
    }

    public void deleteById(Long id) {
        jugadorRepository.deleteById(id);
    }

    public Jugador crearJugador(Jugador jugador) {
        // Validar si ya existe un jugador con el mismo nombre y apellido
        boolean jugadorExistente = jugadorRepository.existsByNombreAndApellido(jugador.getNombre(), jugador.getApellido());
        if (jugadorExistente) {
            throw new IllegalArgumentException("Ya existe un jugador con el mismo nombre y apellido");
        }

        // Guardar el jugador en la base de datos
        return jugadorRepository.save(jugador);
    }
}
