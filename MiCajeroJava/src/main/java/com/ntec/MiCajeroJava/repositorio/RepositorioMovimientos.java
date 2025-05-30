package com.ntec.MiCajeroJava.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ntec.MiCajeroJava.identidad.Cuenta;
import com.ntec.MiCajeroJava.identidad.Movimiento;

public interface RepositorioMovimientos extends JpaRepository<Movimiento, Long> {
   List<Movimiento> findByCuenta(Cuenta cuenta);
   List<Movimiento> findByCuentaOrderByFechaDesc(Cuenta cuenta);

}
