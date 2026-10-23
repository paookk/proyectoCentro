package com.cplp.Centro;
import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "adopcion")
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Adopcion")
    private Integer idAdopcion;

    @Column(name = "id_Adoptante")
    private Integer idAdoptante;

    @Column(name = "id_Mascota")
    private Integer idMascota;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "estado_solicitud")
    private String estadoSolicitud;

    // --- GETTERS (Para leer) ---
    public Integer getIdAdopcion() { return idAdopcion; }
    public Integer getIdAdoptante() { return idAdoptante; }
    public Integer getIdMascota() { return idMascota; }
    public Date getFecha() { return fecha; }
    public String getEstadoSolicitud() { return estadoSolicitud; }

    // --- SETTERS (Para escribir) ---
    public void setIdAdopcion(Integer idAdopcion) { this.idAdopcion = idAdopcion; }
    public void setIdAdoptante(Integer idAdoptante) { this.idAdoptante = idAdoptante; }
    public void setIdMascota(Integer idMascota) { this.idMascota = idMascota; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public void setEstadoSolicitud(String estadoSolicitud) { this.estadoSolicitud = estadoSolicitud; }
}