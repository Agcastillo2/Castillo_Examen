package com.espe.cursos.services;

import com.espe.cursos.models.Entrenador;
import com.espe.cursos.models.EntrenadorJugador;
import com.espe.cursos.models.UsuarioDTO;
import com.espe.cursos.repositories.EntrenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Entrenador> findAll() {
        return entrenadorRepository.findAll();
    }

    public Optional<Entrenador> findById(Long id) {
        return entrenadorRepository.findById(id);
    }

    public Entrenador save(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public void deleteById(Long id) {
        entrenadorRepository.deleteById(id);
    }

    public Optional<EntrenadorJugador> asignarUsuario(Long entrenadorId, Long usuarioId) {
        Optional<Entrenador> entrenadorOptional = entrenadorRepository.findById(entrenadorId);
        if (entrenadorOptional.isPresent()) {
            Entrenador entrenador = entrenadorOptional.get();

            // Validar si el usuario ya está asignado
            boolean usuarioYaAsignado = entrenador.getEntrenadorJugadors().stream()
                    .anyMatch(entrenadorUsuario -> entrenadorUsuario.getJugadorId().equals(usuarioId));
            if (usuarioYaAsignado) {
                throw new IllegalArgumentException("Este usuario ya está asignado a este entrenador");
            }

            try {
                // Llamada al microservicio de usuarios
                UsuarioDTO usuario = restTemplate.getForObject("http://localhost:8001/api/jugadores/" + usuarioId, UsuarioDTO.class);

                if (usuario == null) {
                    throw new IllegalArgumentException("No se encuentra el usuario solicitado o no existe");
                }

                EntrenadorJugador entrenadorUsuario = new EntrenadorJugador();
                entrenadorUsuario.setJugadorId(usuario.getId());
                entrenadorUsuario.setJugadorNombre(usuario.getNombre());
                entrenadorUsuario.setJugadorApellido(usuario.getApellido());

                entrenador.getEntrenadorJugadors().add(entrenadorUsuario);
                entrenadorRepository.save(entrenador);

                return Optional.of(entrenadorUsuario);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error en la base de datos");
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void eliminarUsuarioDeEntrenador(Long entrenadorId, Long usuarioId) {
        Optional<Entrenador> entrenadorOptional = entrenadorRepository.findById(entrenadorId);
        if (entrenadorOptional.isPresent()) {
            Entrenador entrenador = entrenadorOptional.get();
            entrenador.getEntrenadorJugadors().removeIf(entrenadorUsuario -> entrenadorUsuario.getJugadorId().equals(usuarioId));
            entrenadorRepository.save(entrenador);
        }
    }

    public List<Entrenador> findEntrenadoresByJugadorId(Long jugadorId) {
        return entrenadorRepository.findEntrenadoresByJugadorId(jugadorId);
    }

    public List<UsuarioDTO> findJugadoresByEntrenadorId(Long entrenadorId) {
        Optional<Entrenador> entrenadorOptional = entrenadorRepository.findById(entrenadorId);
        if (entrenadorOptional.isPresent()) {
            Entrenador entrenador = entrenadorOptional.get();
            List<UsuarioDTO> jugadores = new ArrayList<>();
            for (EntrenadorJugador entrenadorJugador : entrenador.getEntrenadorJugadors()) {
                // Llamada al microservicio de jugadores para obtener los detalles del jugador
                UsuarioDTO jugador = restTemplate.getForObject("http://localhost:8001/api/jugadores/" + entrenadorJugador.getJugadorId(), UsuarioDTO.class);
                if (jugador != null) {
                    jugadores.add(jugador);
                }
            }
            return jugadores;
        }
        return new ArrayList<>();
    }

}
