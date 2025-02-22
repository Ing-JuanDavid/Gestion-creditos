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
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

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
		logger.info(nl + nl+"<----Gestion de creditos---->" + nl);
		AppGestionCreditos();
	}

	public void AppGestionCreditos(){
		var consola = new Scanner(System.in);
		var salir = false;
		while(!salir){
			try{
				var opcion = mostrarMenu(consola);
				salir = ejecutarOpciones(consola, opcion);
			}catch (Exception e){
				logger.info("Ha ocurrirdo un error: " + e.getMessage() + nl);
			}finally{
				logger.info(nl);
			}
		}
	}

	public int mostrarMenu(Scanner consola){
		logger.info("""
				\t.:MENU:.
				1. Agregar cliente
				2. Crear un credito
				3. Registrar pago
				4. Ver clientes
				5. Buscar creditos por cliente
				6. Salir
				Ecoje una opcion:\s""");
		return Integer.parseInt(consola.nextLine());
	}

	public boolean ejecutarOpciones(Scanner consola, int opcion){
		switch(opcion){
			case 1 -> agregarCliente(consola);
			case 2 -> crearCredito(consola);
			case 3 -> registrarPago(consola);
			case 4 -> {if(!mostrarClientes())logger.info("No hay clientes para mostrar" + nl);}
			case 5 -> buscarCreditosCliente(consola);
			case 6 -> {logger.info("Saliendo..." +nl);return true;}
		}
		return false;
	}

	public void agregarCliente(Scanner consola){
		logger.info(nl + "Agregando cliente----->" + nl);
		logger.info("Id: ");
		var id = Integer.parseInt(consola.nextLine());
		logger.info("Nombre: ");
		var nombre = consola.nextLine();
		Cliente cliente = new Cliente();
		cliente.setIdCliente(id);
		cliente.setNombre(nombre);
		clienteServ.agregarCliente(cliente);
		logger.info("Cliente agregado" + nl);
	}

	public void crearCredito(Scanner consola){
		if(!mostrarClientes()) {
			logger.info("Debe agregar un cliente primero" + nl);
			return;
		}
		logger.info(nl + "Creando credito----->" + nl);
		logger.info("Id del cliente: ");
		var id = Integer.parseInt(consola.nextLine());
		var cliente = clienteServ.buscarCliente(id);

		if(cliente != null){// Si el cliente existe se recolectan los datos para el credito y se crea
			logger.info("Monto: ");
			Double monto = Double.parseDouble(consola.nextLine());
			logger.info("Taza de interes: ");
			float ti = Float.parseFloat(consola.nextLine());
			logger.info("Fecha final (yy-mm-dd): ");
			var fecha = consola.nextLine();

			// Como el metodo setFechaF recibe como argumento un objeto de tipo LocalDate y por consola se obtiene un String,
			// este se debe convertir al tipo requerido
			DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yy-MM-dd");
			LocalDate fechaF = LocalDate.parse(fecha,formateador);

			var credito = new Credito();
			credito.setMonto(new BigDecimal(monto));
			credito.setTi(ti);
			credito.setFechaF(fechaF);
			credito.setCliente(cliente);
			cliente.getCreditos().add(credito);
			clienteServ.agregarCliente(cliente);
			logger.info("Se ha creado el credito" + nl);
		}
		else
			logger.info("No existe un cliente con Id: " + id + nl);

	}

	public void registrarPago(Scanner consola){
		if(!mostrarClientes()) {
			logger.info("Debe agregar un cliente primero" + nl);
			return;
		}
		logger.info(nl + "Realizar pago------>" + nl);
		logger.info("Id cliente: ");
		var idCliente = Integer.parseInt(consola.nextLine());
		Cliente cliente = clienteServ.buscarCliente(idCliente);
		if(cliente == null){
			logger.info("No existe un cliente con Id: " + idCliente + nl);
			return;
		}

		var creditos = cliente.getCreditos();
		if (creditos.isEmpty()){
			logger.info("El cliente " + cliente.getNombre() + " aun no tiene creditos" + nl);
				return;
		}
		logger.info(nl + "Creditos de " + cliente.getNombre() + " ----->" +nl);
		creditos.forEach(credito -> logger.info(credito.toString() + nl));
		logger.info("Seleccione un credito (Id): ");
		var idCredito = Integer.parseInt(consola.nextLine());
		Credito credito = buscarCreditoCliente(idCliente,idCredito);
		if(credito == null){
			logger.info("No existe un credito con Id: " + idCredito + nl);
			return;
		}

		logger.info(nl + "Efectuar pago-----v" + nl);
		logger.info(credito.toString() + " seleccionado" + nl);
		logger.info("Ingrese el valor a cancelar: ");
		BigDecimal valor = BigDecimal.valueOf(Double.parseDouble(consola.nextLine()));

		var pago  = new Pago();
		pago.setCliente(cliente);
		pago.setCredito(credito);
		pago.setValor(valor);
		pagoServ.agregarPago(pago);
		logger.info("Pago registrado!" + nl);

		credito.getPagos().add(pago);
		cliente.getPagos().add(pago);

		logger.info("Pagos------------------" + nl);
		clienteServ.listarPagos(cliente.getIdCliente()).forEach(obj -> logger.info(obj.toString() + nl));
		//cliente.getPagos().forEach(p -> logger.info(p.toString() + nl)); en l alista de creditos el ultimo elemento esta incompleto
		// ya que los datos faltantes los rellena el trigger
	}

	public void buscarCreditosCliente(Scanner consola){
		logger.info(nl + "Buscar creditos----->" + nl);
		logger.info("Id cliente: ");
		var idCliente = Integer.parseInt(consola.nextLine());
		var cliente = clienteServ.buscarCliente(idCliente);
		if(cliente == null){
			logger.info("No existe un cliente con id: " + idCliente + nl);
			return;
		}
		logger.info(nl + "Creditos de " + cliente.getNombre() + "-----v" + nl);
		cliente.getCreditos().forEach( c -> logger.info(c.toString() + nl));
		logger.info("Seleccione un credito: ");
		var idCredito = Integer.parseInt(consola.nextLine());
		var credito = creditoServ.buscarCredito(idCredito);
		if(credito == null){
			logger.info("No existe un credito con id: " + idCredito + nl);
			return;
		}
		logger.info(nl + "Historial de pagos-----v" + nl);
		AtomicInteger cont = new AtomicInteger(0);
		credito.getPagos().forEach(p-> {
			logger.info(p.toString() + nl);
			cont.incrementAndGet();
		});
		logger.info("Cantidad de pagos: " + cont.get() + nl);
	}

	public boolean mostrarClientes(){
		var clientes = clienteServ.listarClientes();
		if(clientes.isEmpty()) return false;
		logger.info(nl + "Clientes----->" + nl);
		clientes.forEach(cliente -> logger.info(cliente.toString()+nl));
		return true;
	}

	public Credito buscarCreditoCliente(Integer idCliente, Integer idCredito){
		var credito = creditoServ.buscarCredito(idCredito);
		if(credito.getCliente().getIdCliente().equals(idCliente))
			return credito;
		return null;
	}
}
