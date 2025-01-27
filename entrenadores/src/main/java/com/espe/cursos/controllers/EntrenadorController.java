package com.espe.cursos.controllers;

import com.espe.cursos.models.Entrenador;
import com.espe.cursos.models.EntrenadorJugador;
import com.espe.cursos.models.UsuarioDTO;
import com.espe.cursos.services.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorController {

    @Autowired
    private EntrenadorService entrenadorService;

    @GetMapping
    public List<Entrenador> listar() {
        return entrenadorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrenador> obtener(@PathVariable Long id) {
        return entrenadorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Entrenador> crear(@RequestBody Entrenador entrenador) {
        return ResponseEntity.ok(entrenadorService.save(entrenador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrenador> actualizarEntrenador(@PathVariable Long id, @RequestBody Entrenador entrenadorActualizado) {
        Optional<Entrenador> entrenadorOptional = entrenadorService.findById(id);
        if (entrenadorOptional.isPresent()) {
            Entrenador entrenadorExistente = entrenadorOptional.get();

            // Actualizamos todos los campos del objeto Entrenador
            if (entrenadorActualizado.getNombre() != null) {
                entrenadorExistente.setNombre(entrenadorActualizado.getNombre());
            }
            if (entrenadorActualizado.getEspecialidad() != null) {
                entrenadorExistente.setEspecialidad(entrenadorActualizado.getEspecialidad());
            }
            if (entrenadorActualizado.getExperiencia() != null) {
                entrenadorExistente.setExperiencia(entrenadorActualizado.getExperiencia());
            }
            if (entrenadorActualizado.getEntrenadorJugadors() != null) {
                entrenadorExistente.setEntrenadorJugadors(entrenadorActualizado.getEntrenadorJugadors());
            }

            Entrenador entrenadorGuardado = entrenadorService.save(entrenadorExistente);
            return ResponseEntity.ok(entrenadorGuardado);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/asignar-usuario/{entrenadorId}")
    public ResponseEntity<?> asignarUsuario(@PathVariable Long entrenadorId, @RequestBody Long usuarioId) {
        Optional<EntrenadorJugador> entrenadorUsuarioOptional = entrenadorService.asignarUsuario(entrenadorId, usuarioId);
        if (entrenadorUsuarioOptional.isPresent()) {
            return ResponseEntity.ok(entrenadorUsuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{entrenadorId}/usuario/{usuarioId}")
    public ResponseEntity<Void> eliminarUsuarioDeEntrenador(@PathVariable Long entrenadorId, @PathVariable Long usuarioId) {
        entrenadorService.eliminarUsuarioDeEntrenador(entrenadorId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        entrenadorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Listar entrenadores de un jugador
    @GetMapping("/jugador/{jugadorId}/entrenadores")
    public ResponseEntity<List<Entrenador>> listarEntrenadoresPorJugador(@PathVariable Long jugadorId) {
        List<Entrenador> entrenadores = entrenadorService.findEntrenadoresByJugadorId(jugadorId);
        if (entrenadores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entrenadores);
    }

    // Listar jugadores entrenados por un entrenador
    @GetMapping("/{entrenadorId}/jugadores")
    public ResponseEntity<List<UsuarioDTO>> listarJugadoresPorEntrenador(@PathVariable Long entrenadorId) {
        List<UsuarioDTO> jugadores = entrenadorService.findJugadoresByEntrenadorId(entrenadorId);
        if (jugadores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jugadores);
    }

}
