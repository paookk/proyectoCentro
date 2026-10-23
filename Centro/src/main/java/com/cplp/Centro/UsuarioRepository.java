package com.cplp.Centro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Esta instrucción le dice a Java: "Busca un usuario por su username"
    Usuario findByUsernameUsuario(String usernameUsuario);
}