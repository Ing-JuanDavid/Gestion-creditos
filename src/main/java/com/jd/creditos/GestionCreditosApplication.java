package com.jd.creditos;

import com.jd.creditos.modelos.Cliente;
import com.jd.creditos.modelos.Credito;
import com.jd.creditos.modelos.Pago;
import com.jd.creditos.servicios.IClienteServ;
import com.jd.creditos.servicios.ICreditoServ;
import com.jd.creditos.servicios.IPagoServ;
import com.jd.creditos.servicios.imp.ClienteServImp;
import com.jd.creditos.servicios.imp.CreditoServImp;
import com.jd.creditos.servicios.imp.PagoServImp;
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

	@Autowired
	private ICreditoServ creditoServ = new CreditoServImp();

	@Autowired
	private IPagoServ pagoServ = new PagoServImp();

	public static void main(String[] args) {
		logger.info("Iniciando app");
		SpringApplication.run(GestionCreditosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Prueba");
		var cliente = clienteServ.buscarCliente(1064606279);
		logger.info("Mostrando creditos de " + cliente.getIdCliente());
		clienteServ.listarCreditos(cliente.getIdCliente()).forEach(System.out::println);
		// Agregando un pago al credito 5
		var credito = creditoServ.buscarCredito(5);
//		var pago = new Pago();
//		pago.setValor(new BigDecimal(50000));
//		pago.setFecha(LocalDate.now());
//		pago.setCliente(cliente);
//		pago.setCredito(credito);
//		credito.getPagos().add(pago);
//		creditoServ.agregarCredito(credito);

		var listaPagos = credito.getPagos();
		if (!listaPagos.isEmpty())
			listaPagos.forEach(System.out::println);
		else logger.info("No hay pagos para este credito");
	}
}
