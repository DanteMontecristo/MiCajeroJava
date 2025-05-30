package com.ntec.MiCajeroJava.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ntec.MiCajeroJava.identidad.*;
import com.ntec.MiCajeroJava.servicios.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ControladorAdmin {
    // Controlador para la administración de clientes y cuentas
    private final ServicioCliente servicioCliente;
    private final ServicioCuenta servicioCuenta;

    @GetMapping
    public String adminHome(){
        return "admin/index";
    }

    @GetMapping("/crear-cliente")
    public String mostrarFormularioCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "admin/crear-cliente";
    }

    @PostMapping("/crear-cliente")
    public String crearCliente(@ModelAttribute Cliente cliente) {
        servicioCliente.crearCliente(cliente);       
        return "redirect:/admin";
    }
    
    @GetMapping("/crear-cuenta")
    public String mostrarFormularioCuenta(Model model) {
        model.addAttribute("cuenta", new Cuenta());
        return "admin/crear-cuenta";
    }
    
    @PostMapping("/crear-cuenta")
    public String crearCuenta(@ModelAttribute Cuenta cuenta) {
        servicioCuenta.crearCuenta(cuenta.getCliente(), cuenta.getNumero(),
        cuenta.getTipo(), cuenta.getSaldo());
        return "redirect:/admin";
    }

    @GetMapping("/desbloquear")
    public String mostrarDesbloqueo() {
        return "admin/desbloquear";
    }

    @PostMapping("/desbloquear")
    public String desbloquearCuenta(@RequestParam String identificacion,
    @RequestParam String nuevoPin) {
        servicioCliente.desbloquearCliente(identificacion, nuevoPin);
        return "redirect:/admin";
    }   
}
