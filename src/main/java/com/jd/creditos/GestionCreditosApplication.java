package com.jd.creditos;

import com.jd.creditos.modelos.Cliente;
import com.jd.creditos.modelos.Credito;
import com.jd.creditos.servicios.IClienteServ;
import com.jd.creditos.servicios.imp.ClienteServImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class GestionCreditosApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(GestionCreditosApplication.class);

	@Autowired
	private IClienteServ clienteServ = new ClienteServImp();

	public static void main(String[] args) {
		logger.info("Iniciando app");
		SpringApplication.run(GestionCreditosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Prueba");
		var cliente = clienteServ.buscarCliente(1064606279);

		var credito = new Credito();
		credito.setMonto(new BigDecimal(500000));
		credito.setFechaF(LocalDate.of(2025, 6, 6));
		credito.setTi(.1f);
		credito.setCliente(cliente); // Establecer relación bidireccional

		cliente.getCreditos().add(credito); // Agregar crédito a la lista de créditos del cliente
//
//		// Guardar el cliente (Hibernate guardará automáticamente los créditos gracias a CascadeType.ALL)
		clienteServ.agregarCliente(cliente);
//		logger.info("Cliente agregado");
		clienteServ.listarCreditos(1064606279).forEach(System.out::println);

		var creditos = clienteServ.listarCreditos(1064606279);
	}
}
