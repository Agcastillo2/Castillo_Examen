package com.espe.usuarios.controllers;

import com.espe.usuarios.models.Jugador;
import com.espe.usuarios.services.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
    @RequestMapping("/api/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @GetMapping
    public List<Jugador> listar() {
        return jugadorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtener(@PathVariable Long id) {
        Optional<Jugador> jugador = jugadorService.findById(id);
        if (jugador.isPresent()) {
            return ResponseEntity.ok(jugador.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Jugador> crear(@RequestBody Jugador jugador) {
        return ResponseEntity.ok(jugadorService.save(jugador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizar(@PathVariable Long id, @RequestBody Jugador jugador) {
        Optional<Jugador> jugadorOptional = jugadorService.findById(id);
        if (jugadorOptional.isPresent()) {
            Jugador jugadorExistente = jugadorOptional.get();
            jugadorExistente.setNombre(jugador.getNombre());
            jugadorExistente.setApellido(jugador.getApellido());
            jugadorExistente.setEmail(jugador.getEmail());
            jugadorExistente.setTelefono(jugador.getTelefono());
            jugadorExistente.setFechaNacimiento(jugador.getFechaNacimiento());
            jugadorExistente.setNumeroCamiseta(jugador.getNumeroCamiseta());
            jugadorExistente.setPosicion(jugador.getPosicion());
            return ResponseEntity.ok(jugadorService.save(jugadorExistente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        jugadorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
