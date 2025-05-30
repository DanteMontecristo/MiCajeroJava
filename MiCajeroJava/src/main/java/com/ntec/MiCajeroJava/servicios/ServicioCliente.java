package com.ntec.MiCajeroJava.servicios;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.ntec.MiCajeroJava.identidad.Cliente;
import com.ntec.MiCajeroJava.repositorio.RepositorioCliente;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioCliente {

    private final RepositorioCliente repositorioCliente;

    public Cliente crearCliente(Cliente cliente) {
        cliente.setBloqueado(false);
        cliente.setIntentosFallidos(0);
        return repositorioCliente.save(cliente);
    }

    public Optional<Cliente> buscarPorIdentificacion(String identificacion) {
        return repositorioCliente.findByIdentificacion(identificacion);
    }

    public boolean validarPin(Cliente cliente, String pin) {
        if (cliente.isBloqueado()) return false;

        if (cliente.getPin().equals(pin)) {
            cliente.setIntentosFallidos(0);
            repositorioCliente.save(cliente);
            return true;
        } else {
            int intentos = cliente.getIntentosFallidos() + 1;
            cliente.setIntentosFallidos(intentos);
            if (intentos >= 3) {
                cliente.setBloqueado(true);
            }
            repositorioCliente.save(cliente);
            return false;
        }
    }

    public void desbloquearCliente(String identificacion, String nuevoPin) {
        Optional<Cliente> optionalCliente = repositorioCliente.findByIdentificacion(identificacion);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setBloqueado(false);
            cliente.setIntentosFallidos(0);
            cliente.setPin(nuevoPin);
            repositorioCliente.save(cliente);
        }
    }

    public void cambiarPin(Cliente cliente, String nuevoPin) {
        cliente.setPin(nuevoPin);
        repositorioCliente.save(cliente);
    }

    public void incrementarIntento(Cliente cliente) {
        cliente.setIntentos(cliente.getIntentos() + 1);
        repositorioCliente.save(cliente);
    }

    public void reiniciarIntentos(Cliente cliente) {
        cliente.setIntentos(0);
        repositorioCliente.save(cliente);
    }

    public void bloquearCliente(Cliente cliente) {
        cliente.setBloqueado(true);
        repositorioCliente.save(cliente);
    }
}
