package com.cplp.Centro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controlador {

    // 1. TODOS LOS REPOSITORIOS VAN AQUÍ ARRIBA
    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private RolRepository rolRepo;

    @Autowired
    private AdopcionRepository adopcionRepo;

    @Autowired
    private MascotaRepository mascotaRepo;

    // 2. RUTAS DE NAVEGACIÓN
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/login")   // Manda a llamar a estos datos para ingresar
    public String login(@RequestParam String nombre,
                        @RequestParam String apellido,
                        @RequestParam String username,
                        @RequestParam String id,
                        @RequestParam String password,
                        Model model) {

        // Java busca al usuario en tu MySQL
        Usuario usuarioBD = usuarioRepo.findByUsernameUsuario(username);

        // Verifica si el usuario existe y si la contraseña coincide
        if (usuarioBD != null && usuarioBD.getPasswordUsuario().equals(password)) {
            if (usuarioBD.getRol() != null && usuarioBD.getRol().getNombreRol().equals("ADMIN")) {
                model.addAttribute("nombre", nombre);
                model.addAttribute("apellido", apellido);
                return "bienvenido"; // Va al panel de Admin
            } else {
                // ¡Si es un usuario normal (adoptante), lo mandamos a ver a los gatitos!
                return "redirect:/catalogo";
            }
        }

        // Si el usuario no existe o la contraseña está mal
        model.addAttribute("error", "Usuario o contraseña incorrectos.");
        return "index";
    }

    // Esto muestra la pantalla visual de registro
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    // Esto toma los datos y los guarda en MySQL
    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@RequestParam String username,
                                 @RequestParam String password,
                                 Model model) {

        // Verifica que el usuario no exista ya
        if (usuarioRepo.findByUsernameUsuario(username) != null) {
            model.addAttribute("error", "Ese username ya existe, elige otro.");
            return "registro";
        }

        // Crea el usuario vacío y lo llena con los datos
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsernameUsuario(username);
        nuevoUsuario.setPasswordUsuario(password);

        // Le asignamos el rol 3 (Que en tu base de datos debería ser 'USUARIO' normal)
        Rol rolNormal = rolRepo.findById(3).orElse(null);
        nuevoUsuario.setRol(rolNormal);

        // ¡Lo guarda en MySQL!
        usuarioRepo.save(nuevoUsuario);

        // Lo regresa a la pantalla de inicio de sesión
        return "redirect:/";
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        // Busca todas las mascotas en tu base de datos
        model.addAttribute("mascotas", mascotaRepo.findAll());
        return "catalogo"; // Mostrará la página web catalogo.html
    }

    // ==========================================
    // RUTAS DEL PANEL DE ADMINISTRADOR
    // ==========================================

    // 1. MUESTRA LA PANTALLA Y LA TABLA
    @GetMapping("/admin/modificar")
    public String adminModificar(Model model) {
        // Busca todas las mascotas y las envía a la tabla
        model.addAttribute("mascotas", mascotaRepo.findAll());
        return "admin-modificar";
    }

    // 2. GUARDA UNA MASCOTA NUEVA (CREAR)
    @PostMapping("/admin/guardarMascota")
    public String guardarMascota(@RequestParam String nombre,
                                 @RequestParam Integer idEspecie,
                                 @RequestParam String edad,
                                 @RequestParam String sexo,
                                 @RequestParam String raza,
                                 @RequestParam String tamano,
                                 @RequestParam String estadoAdopcion) {
        Mascota m = new Mascota();
        m.setNombre(nombre);
        m.setIdEspecie(idEspecie);
        m.setEdad(edad);
        m.setSexo(sexo);
        m.setRaza(raza);
        m.setTamano(tamano);
        m.setEstadoAdopcion(estadoAdopcion);

        mascotaRepo.save(m); // Lo guarda en MySQL
        return "redirect:/admin/modificar"; // Recarga la página
    }

    // 3. ELIMINAR MASCOTA (BORRAR)
    @GetMapping("/admin/eliminarMascota")
    public String eliminarMascota(@RequestParam Integer id) {
        mascotaRepo.deleteById(id); // Lo borra de MySQL
        return "redirect:/admin/modificar"; // Recarga la página
    }

    // 1. MUESTRA LA TABLA DE SOLICITUDES
    @GetMapping("/admin/solicitudes")
    public String adminSolicitudes(Model model) {
        // Manda todas las solicitudes a la página
        model.addAttribute("solicitudes", adopcionRepo.findAll());
        return "admin-solicitudes";
    }

    // 2. APRUEBA UNA SOLICITUD
    @GetMapping("/admin/aprobarSolicitud")
    public String aprobarSolicitud(@RequestParam Integer id) {
        // Busca la solicitud
        Adopcion solicitud = adopcionRepo.findById(id).orElse(null);

        if (solicitud != null) {
            // Le cambia el estado a Aprobada
            solicitud.setEstadoSolicitud("Aprobada");
            adopcionRepo.save(solicitud);

            // ¡Magia extra! Cambiamos también a la mascota a "Adoptado"
            Mascota mascota = mascotaRepo.findById(solicitud.getIdMascota()).orElse(null);
            if(mascota != null) {
                mascota.setEstadoAdopcion("Adoptado");
                mascotaRepo.save(mascota);
            }
        }
        return "redirect:/admin/solicitudes"; // Recarga la página
    }

    @GetMapping("/admin/adoptantes")
    public String adminAdoptantes() {
        return "admin-adoptantes"; // Mostrará el archivo admin-adoptantes.html
    }

    @GetMapping("/admin/estadisticas")
    public String adminEstadisticas() {
        return "admin-estadisticas"; // Mostrará el archivo admin-estadisticas.html
    }
}