package com.espe.cursos.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entrenadores")
@Data
public class Entrenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 255, message = "La especialidad no puede tener más de 255 caracteres")
    private String especialidad;

    @NotNull(message = "La experiencia es obligatoria")
    @Min(value = 1, message = "La experiencia debe ser un número positivo mayor o igual a 1")
    private Integer experiencia;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "entrenador_id")
    private List<EntrenadorJugador> entrenadorJugadors = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(Integer experiencia) {
        this.experiencia = experiencia;
    }

    public List<EntrenadorJugador> getEntrenadorJugadors() {
        return entrenadorJugadors;
    }

    public void setEntrenadorJugadors(List<EntrenadorJugador> entrenadorJugadors) {
        this.entrenadorJugadors = entrenadorJugadors;
    }
}
