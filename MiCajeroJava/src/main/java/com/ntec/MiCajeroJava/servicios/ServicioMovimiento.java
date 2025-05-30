package com.ntec.MiCajeroJava.servicios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ntec.MiCajeroJava.identidad.Cuenta;
import com.ntec.MiCajeroJava.identidad.Movimiento;
import com.ntec.MiCajeroJava.identidad.TipoMovimiento;
import com.ntec.MiCajeroJava.repositorio.RepositorioCuenta;
import com.ntec.MiCajeroJava.repositorio.RepositorioMovimientos;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioMovimiento {

    private final RepositorioMovimientos repositirioMovimiento;
    private final RepositorioCuenta repositorioCuenta;

    public Movimiento registrarMovimiento(Cuenta cuenta, 
    TipoMovimiento tipo, 
    double monto){
        Movimiento movimiento = Movimiento.builder()
            .cuenta(cuenta)
            .tipo(tipo)
            .monto(monto)
            .fecha(LocalDateTime.now())
            .build();
        return repositirioMovimiento.save(movimiento);
    }

    public List<Movimiento> obtenerMovimientosPorCuenta(Cuenta cuenta,
    double monto) {
        return repositirioMovimiento.findByCuenta(cuenta);
    }

    public boolean realizarRetiro(Cuenta cuenta, double monto){
        if (cuenta.getSaldo() >= monto) {
            cuenta.setSaldo(cuenta.getSaldo() - monto);
            repositorioCuenta.save(cuenta);
            registrarMovimiento(cuenta, TipoMovimiento.RETIRO, monto);
            return true;       
            
        }
        return false;
    }

    public boolean realizarTransferencia(Cuenta origen, 
    Cuenta destino, double monto) {
        if (origen.getSaldo()>= monto) {
            origen.setSaldo(origen.getSaldo()- monto);
            destino.setSaldo(destino.getSaldo() + monto);
            repositorioCuenta.save(origen);
            repositorioCuenta.save(destino);

            registrarMovimiento(origen, TipoMovimiento.TRANSFERENCIA, -monto);
            registrarMovimiento(destino, TipoMovimiento.TRANSFERENCIA, monto);
            return true;
        }
        return false;
    }

    public List<Movimiento> buscarPorCuenta(String numeroCuenta){
        Cuenta cuenta = repositorioCuenta.findByNumero(numeroCuenta)
            .orElseThrow(() -> 
            new RuntimeException("Cuenta no encontrada"));
            return repositirioMovimiento
            .findByCuentaOrderByFechaDesc(cuenta);
    }

    public boolean realizarConsignacion(Cuenta cuenta, double monto){
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        cuenta.setSaldo(cuenta.getSaldo() + monto);
        repositorioCuenta.save(cuenta);
        registrarMovimiento(cuenta, TipoMovimiento.CONSIGNACION, monto);
        return true;
    }


}
