package com.jd.creditos.repositorios;

import com.jd.creditos.modelos.Credito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICreditoRepo extends JpaRepository<Credito,Integer> {
}
