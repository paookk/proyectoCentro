package com.caoc.centro_adopcion.controller;

import com.caoc.centro_adopcion.model.Adoptante;
import com.caoc.centro_adopcion.model.Adopcion;
import com.caoc.centro_adopcion.repository.AdoptanteRepository;
import com.caoc.centro_adopcion.repository.AdopcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;

@Controller
public class AuthController {

    @Autowired
    private AdoptanteRepository adoptanteRepository;

    @Autowired
    private AdopcionRepository adopcionRepository;

    // Esta ruta carga tu pantalla de login al entrar a localhost:8080/ o localhost:8080/index
    @GetMapping({"/", "/index"})
    public String mostrarLogin() {
        return "index";
    }

    // La ruta ahora es /login para coincidir con tu index.html
    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam("nombre") String nombre,
            @RequestParam("password") String password,
            @RequestParam("rol") String rol,
            Model model) {

        if ("ADMIN".equals(rol)) {
            // VALIDACIÓN DE ADMIN: Solo permite entrar si el usuario es "carlos" y la contraseña es "1234"
            if ("carlos".equals(nombre) && "1234".equals(password)) {

                // CAMBIO APLICADO: Redirige directamente a la tabla de solicitudes
                return "redirect:/admin/solicitudes";

            } else {
                model.addAttribute("error", "Usuario o contraseña de administrador incorrectos");
                return "index";
            }

        } else {
            // Busca al adoptante en la base de datos MySQL
            Adoptante adoptante = adoptanteRepository.findByNombreAndPassword(nombre, password);

            if (adoptante != null) {
                // Redirige al apartado de mascotas al iniciar sesión
                return "redirect:/mascotas";
            } else {
                // Si no existe, muestra el error en la pantalla de login
                model.addAttribute("error", "Usuario o contraseña incorrectos");
                return "index";
            }
        }
    }

    // 1. Esta ruta muestra la página de registro cuando el usuario hace clic en el enlace
    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "registro";
    }

    // 2. Esta ruta recibe los datos del formulario y los guarda en MySQL
    @PostMapping("/procesar-registro")
    public String registrarNuevoUsuario(@ModelAttribute Adoptante nuevoAdoptante) {

        // El repositorio guarda automáticamente el nuevo adoptante en la base de datos
        adoptanteRepository.save(nuevoAdoptante);

        // Redirigimos de vuelta a la pantalla de login (index)
        return "redirect:/index";

    }

    // 3. Esta ruta recibe el formulario del modal y guarda la solicitud en la base de datos
    @PostMapping("/enviar-solicitud")
    public String enviarSolicitudAdopcion(
            @RequestParam("idMascota") Long idMascota,
            @RequestParam("motivo") String motivo) {

        Adopcion nuevaAdopcion = new Adopcion();
        nuevaAdopcion.setIdMascota(idMascota);
        nuevaAdopcion.setIdAdoptante(1L); // ID temporal para asociar la solicitud
        nuevaAdopcion.setFecha(LocalDate.now());
        nuevaAdopcion.setEstadoSolicitud("Pendiente");

        adopcionRepository.save(nuevaAdopcion);

        return "redirect:/mascotas";
    }
}