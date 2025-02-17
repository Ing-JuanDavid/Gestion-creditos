package com.jd.creditos.repositorios;

import com.jd.creditos.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IClienteRepo extends JpaRepository<Cliente,Integer> {
}
