package com.caoc.centro_adopcion.controller;

import com.caoc.centro_adopcion.model.Adopcion;
import com.caoc.centro_adopcion.model.Adoptante;
import com.caoc.centro_adopcion.model.Mascota;
import com.caoc.centro_adopcion.repository.AdopcionRepository;
import com.caoc.centro_adopcion.repository.AdoptanteRepository;
import com.caoc.centro_adopcion.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdopcionRepository adopcionRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private AdoptanteRepository adoptanteRepository; // Repositorio para gestionar usuarios/adoptantes

    // Muestra el dashboard principal de administrador
    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        model.addAttribute("nombreUsuario", "Administrador");
        return "bienvenido";
    }

    // Lista todas las solicitudes de adopción desde MySQL
    @GetMapping("/solicitudes")
    public String listarSolicitudes(Model model) {
        List<Adopcion> solicitudes = adopcionRepository.findAll();
        model.addAttribute("solicitudes", solicitudes);
        return "solicitudes";
    }

    // Aprobar la solicitud y actualizar la mascota a "No disponible"
    @GetMapping("/solicitudes/aprobar/{id}")
    public String aprobarSolicitud(@PathVariable("id") Long id) {
        Adopcion adopcion = adopcionRepository.findById(id).orElse(null);
        if (adopcion != null) {
            // 1. Actualizar el estado de la solicitud
            adopcion.setEstadoSolicitud("Aprobada");
            adopcionRepository.save(adopcion);

            // 2. Buscar la mascota y cambiar su estado a "No disponible"
            Mascota mascota = mascotaRepository.findById(adopcion.getIdMascota()).orElse(null);
            if (mascota != null) {
                mascota.setEstadoAdopcion("No disponible");
                mascotaRepository.save(mascota);
            }
        }
        return "redirect:/admin/solicitudes";
    }

    // Rechazar la solicitud
    @GetMapping("/solicitudes/rechazar/{id}")
    public String rechazarSolicitud(@PathVariable("id") Long id) {
        Adopcion adopcion = adopcionRepository.findById(id).orElse(null);
        if (adopcion != null) {
            adopcion.setEstadoSolicitud("Rechazada");
            adopcionRepository.save(adopcion);
        }
        return "redirect:/admin/solicitudes";
    }

    // ==========================================
    // MÓDULO DE USUARIOS / ADOPTANTES
    // ==========================================

    // Lista todos los usuarios registrados en el sistema
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Adoptante> usuarios = adoptanteRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios"; // Carga el archivo usuarios.html
    }

    // ==========================================
    // MÓDULO DE MASCOTAS / ESPECIES
    // ==========================================

    // Lista las mascotas en la vista especies.html
    @GetMapping("/mascotas")
    public String listarMascotas(Model model) {
        List<Mascota> mascotas = mascotaRepository.findAll();
        model.addAttribute("mascotas", mascotas);
        return "especies";
    }

    // Guarda una nueva mascota desde el formulario de especies
    @PostMapping("/mascotas/guardar")
    public String guardarMascota(@ModelAttribute Mascota mascota) {
        if (mascota.getEstadoAdopcion() == null || mascota.getEstadoAdopcion().isEmpty()) {
            mascota.setEstadoAdopcion("Disponible");
        }
        mascotaRepository.save(mascota);
        return "redirect:/admin/mascotas";
    }

    // Elimina una mascota por su ID
    @GetMapping("/mascotas/eliminar/{id}")
    public String eliminarMascota(@PathVariable("id") Long id) {
        mascotaRepository.deleteById(id);
        return "redirect:/admin/mascotas";
    }

    // ==========================================
    // MÓDULO DE REPORTES Y ESTADÍSTICAS
    // ==========================================
    @GetMapping("/reportes")
    public String verReportes(Model model) {
        long totalMascotas = mascotaRepository.count();
        long totalSolicitudes = adopcionRepository.count();
        long totalUsuarios = adoptanteRepository.count();

        // Contar cuántas mascotas están disponibles
        long mascotasDisponibles = mascotaRepository.findAll().stream()
                .filter(m -> "Disponible".equalsIgnoreCase(m.getEstadoAdopcion()))
                .count();

        long mascotasAdoptadas = totalMascotas - mascotasDisponibles;

        model.addAttribute("totalMascotas", totalMascotas);
        model.addAttribute("mascotasDisponibles", mascotasDisponibles);
        model.addAttribute("mascotasAdoptadas", mascotasAdoptadas);
        model.addAttribute("totalSolicitudes", totalSolicitudes);
        model.addAttribute("totalUsuarios", totalUsuarios);

        return "reportes"; // Carga el archivo reportes.html
    }
}