package com.cplp.Centro;
import jakarta.persistence.*;

@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Mascota")
    private Integer idMascota;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "id_Especie")
    private Integer idEspecie;

    @Column(name = "edad")
    private String edad;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "raza")
    private String raza;

    @Column(name = "tamano")
    private String tamano;

    @Column(name = "estado_adopcion")
    private String estadoAdopcion;

    // --- GETTERS (Para leer) ---
    public Integer getIdMascota() { return idMascota; }
    public String getNombre() { return nombre; }
    public Integer getIdEspecie() { return idEspecie; }
    public String getEdad() { return edad; }
    public String getSexo() { return sexo; }
    public String getRaza() { return raza; }
    public String getTamano() { return tamano; }
    public String getEstadoAdopcion() { return estadoAdopcion; }

    // --- SETTERS (Para escribir) ---
    public void setIdMascota(Integer idMascota) { this.idMascota = idMascota; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setIdEspecie(Integer idEspecie) { this.idEspecie = idEspecie; }
    public void setEdad(String edad) { this.edad = edad; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public void setRaza(String raza) { this.raza = raza; }
    public void setTamano(String tamano) { this.tamano = tamano; }
    public void setEstadoAdopcion(String estadoAdopcion) { this.estadoAdopcion = estadoAdopcion; }
}