package com.cplp.Centro;
import jakarta.persistence.*;

@Entity
@Table(name = "Rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Rol")
    private Integer idRol;

    @Column(name = "nombre_rol")
    private String nombreRol;

    public String getNombreRol() { return nombreRol; }
}