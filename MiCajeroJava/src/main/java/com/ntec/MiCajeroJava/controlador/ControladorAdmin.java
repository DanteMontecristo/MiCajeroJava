package com.ntec.MiCajeroJava.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ntec.MiCajeroJava.identidad.Cliente;
import com.ntec.MiCajeroJava.identidad.Cuenta;
import com.ntec.MiCajeroJava.identidad.TipoCuenta;
import com.ntec.MiCajeroJava.servicios.ServicioCliente;
import com.ntec.MiCajeroJava.servicios.ServicioCuenta;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    public String crearCuenta(@RequestParam String identificacion,
                            @RequestParam String numero,
                            @RequestParam TipoCuenta tipo,
                            @RequestParam double saldo) {
        Cliente cliente = servicioCliente.buscarPorIdentificacion
        (identificacion).orElseThrow();
        servicioCuenta.crearCuenta(cliente, numero, tipo, saldo);
        
        return "redirect:/admin";
    }

    @GetMapping("/desbloquear")
    public String mostrarDesbloqueo() {
        return "admin/desbloquear";
    }

    @PostMapping("/desbloquear")
    public String desbloquearCuenta(@RequestParam String identificacion,
    @RequestParam String nuevoPIN) {
        servicioCliente.desbloquearCliente(identificacion, nuevoPIN);
        return "redirect:/admin";
    }   
}
