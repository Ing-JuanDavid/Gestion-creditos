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
import java.util.Scanner;

@SpringBootApplication
public class GestionCreditosApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(GestionCreditosApplication.class);
	private final String nl = System.lineSeparator();

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
		iniciarAppGestionCreditos();
	}

	public void iniciarAppGestionCreditos(){
		Scanner ent = new Scanner(System.in);
		boolean salir = false;
		logger.info(nl + nl+"<----Gestion de creditos---->" + nl);
		while(!salir){
			try{
				var opcion = mostrarMenu(ent);
				salir = ejecutarOpciones(ent, opcion);
			}catch (Exception e){
				logger.info(nl + "Ha ocurrirdo un error: " + e.getMessage());
			}finally{
				logger.info(nl);
			}
		}
	}

	public int mostrarMenu(Scanner ent){

		logger.info("""
				\t.:MENU:.
				1. Agregar cliente
				2. Crear un credito
				3. Registrar pago
				4. Ver clientes
				5. Salir
				Ecoje una opcion:\s""");
		return Integer.parseInt(ent.nextLine());
	}

	public boolean ejecutarOpciones(Scanner ent, int opcion){
		switch(opcion){
			case 1 -> agregarCliente(ent);
			case 2 -> {return true;}
		}
		return false;
	}

	public void agregarCliente(Scanner ent){
		logger.info("Agregando cliente----->" + nl);
		logger.info("Id: ");
		var id = Integer.parseInt(ent.nextLine());
		logger.info("Nombre: ");
		var nombre = ent.nextLine();
		Cliente cliente = new Cliente();
		cliente.setIdCliente(id);
		cliente.setNombre(nombre);
		clienteServ.agregarCliente(cliente);
		logger.info("Cliente agregado" + nl);
	}
}
