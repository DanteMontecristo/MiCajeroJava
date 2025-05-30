package com.ntec.MiCajeroJava.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.ntec.MiCajeroJava.identidad.*;
import com.ntec.MiCajeroJava.repositorio.RepositorioCuenta;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioCuenta {
     private final RepositorioCuenta repositorioCuenta;
     public Cuenta crearCuenta(Cliente cliente, 
     String numero, TipoCuenta tipo, double saldoInicial){
        Cuenta cuenta = Cuenta.builder()
        .cliente(cliente)
        .numero(numero)
        .tipo(tipo)
        .saldo(saldoInicial)
        .build();
        return repositorioCuenta.save(cuenta);
    }

    public Optional<Cuenta> buscarPorNumero(String numero){
        return repositorioCuenta.findByNumero(numero);
    }

    public double consultarSaldo(Cuenta cuenta){
        return cuenta.getSaldo();
    }

    public List<Cuenta> obtenerCuentasCliente(Cliente cliente){
        return cliente.getCuentas(); 
    }

    public void actualizarSaldo(Cuenta cuenta, double nuevoSaldo) {
        cuenta.setSaldo(nuevoSaldo);
        repositorioCuenta.save(cuenta);
    }

    public List<Cuenta> buscarPorCliente(Cliente cliente) {
        return repositorioCuenta.findByCliente(cliente);
    }

    public Cuenta obtenerCuentaPorClienteActual(String username){
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}