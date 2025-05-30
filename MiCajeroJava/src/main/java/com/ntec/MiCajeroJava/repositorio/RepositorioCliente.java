package com.ntec.MiCajeroJava.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntec.MiCajeroJava.identidad.Cliente;

public interface RepositorioCliente extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByIdentificacion(String identificacion);
    

}
