package com.cplp.Centro;
import jakarta.persistence.*;

@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Usuario")
    private Integer idUsuario;

    @Column(name = "username_Usuario")
    private String usernameUsuario;

    @Column(name = "password_Usuario")
    private String passwordUsuario;

    @ManyToOne
    @JoinColumn(name = "id_Rol")
    private Rol rol;

    public String getUsernameUsuario() { return usernameUsuario; }
    public String getPasswordUsuario() { return passwordUsuario; }
    public Rol getRol() { return rol; }

    public void setUsernameUsuario(String usernameUsuario) { this.usernameUsuario = usernameUsuario; }
    public void setPasswordUsuario(String passwordUsuario) { this.passwordUsuario = passwordUsuario; }
    public void setRol(Rol rol) { this.rol = rol; }
}