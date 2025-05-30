package com.ntec.MiCajeroJava.servicios;

import org.springframework.stereotype.Service;

import com.ntec.MiCajeroJava.identidad.Cliente;
import com.ntec.MiCajeroJava.identidad.Cuenta;
import com.ntec.MiCajeroJava.repositorio.RepositorioCliente;
import com.ntec.MiCajeroJava.repositorio.RepositorioCuenta;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioRetiro {

    private final RepositorioCuenta repositorioCuenta;
    private final RepositorioCliente repositorioCliente;
    private final ServicioMovimiento servicioMovimiento;

    public String realizarRetiro( String identificacion, String numeroCuenta, double monto){
        Cliente cliente = repositorioCliente.findByIdentificacion(identificacion)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Cuenta cuenta = repositorioCuenta.findByNumero(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        if (!cuenta.getCliente().equals(cliente)) {
            throw new RuntimeException("La cuenta no pertenece al cliente");
        }

        if(cliente.isBloqueado()){
            throw new RuntimeException("El cliente o su cuenta está bloqueada");
        }

        boolean exito = servicioMovimiento.realizarRetiro(cuenta, monto);

        if (!exito) {
            throw new RuntimeException("Saldo insuficiente");
        }

        return "redirect:cajero/menu?mensaje=Retiro realizado con éxito";
    }

}
