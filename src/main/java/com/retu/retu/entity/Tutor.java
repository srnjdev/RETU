package com.retu.retu.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tutor")
public class Tutor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "correo", length = 100, nullable = false, unique = true)
    private String correo;

    @Column(name = "materia", length = 100, nullable = false)
    private String materia;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    public Tutor() {
    }

    public Tutor(String nombre, String correo, String materia, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.materia = materia;
        this.contrasena = contrasena;
    }

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

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMateria() {
        return materia;
    }
    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
