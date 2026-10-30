package com.caoc.centro_adopcion.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "adopciones")
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adopcion")
    private Long idAdopcion;

    @Column(name = "id_mascota")
    private Long idMascota;

    @Column(name = "id_adoptante")
    private Long idAdoptante;

    private LocalDate fecha;

    @Column(name = "estado_solicitud")
    private String estadoSolicitud;

    // Getters y Setters
    public Long getIdAdopcion() { return idAdopcion; }
    public void setIdAdopcion(Long idAdopcion) { this.idAdopcion = idAdopcion; }

    public Long getIdMascota() { return idMascota; }
    public void setIdMascota(Long idMascota) { this.idMascota = idMascota; }

    public Long getIdAdoptante() { return idAdoptante; }
    public void setIdAdoptante(Long idAdoptante) { this.idAdoptante = idAdoptante; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstadoSolicitud() { return estadoSolicitud; }
    public void setEstadoSolicitud(String estadoSolicitud) { this.estadoSolicitud = estadoSolicitud; }
}