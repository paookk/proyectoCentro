package com.caoc.centro_adopcion.controller;

import com.caoc.centro_adopcion.model.Mascota;
import com.caoc.centro_adopcion.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @GetMapping("/mascotas")
    public String verCatalogoMascotas(Model model) {
        List<Mascota> mascotas = mascotaRepository.findAll();
        model.addAttribute("mascotas", mascotas);
        return "mascotas"; // Asegúrate de que tu archivo HTML del usuario se llame mascotas.html
    }
}