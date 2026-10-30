package com.caoc.centro_adopcion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class usuario {


    // Ruta para el nuevo panel de Usuario
    @GetMapping("/panel-usuario")
    public String verPanelUsuario() {
        return "panel_usuario"; // Retorna el nuevo HTML que creamos hoy
    }
}