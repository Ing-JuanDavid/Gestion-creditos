package com.jd.creditos.repositorios;

import com.jd.creditos.modelos.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagoRepo extends JpaRepository<Pago,Integer> {
}
