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
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
				logger.info("Ha ocurrido un error: " + e.getCause());
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
				3. Editar credito
				4. Registrar pago
				5. Ver clientes
				6. Buscar creditos por cliente
				7. Salir
				Ecoje una opcion:\s""");
		return Integer.parseInt(consola.nextLine());
	}

	public boolean ejecutarOpciones(Scanner consola, int opcion){
		switch(opcion){
			case 1 -> agregarCliente(consola);
			case 2 -> crearCredito(consola);
			case 3 -> editarCredito(consola);
			case 4 -> registrarPago(consola);
			case 5 -> {if(!mostrarClientes())logger.info("No hay clientes para mostrar" + nl);}
			case 6 -> buscarCreditosCliente(consola);
			case 7 -> {logger.info("Saliendo..." +nl);return true;}
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
			var credito = new Credito();
			credito.setFechaI(LocalDate.now());
			rellenarCredito(credito,consola);
			credito.setCliente(cliente);
			creditoServ.agregarCredito(credito); // Se agrega el credito a la bd
			logger.info("Se ha creado el credito" + nl);
			cliente.getCreditos().add(credito);
			mostrarCreditos(cliente);
		}
		else
			logger.info("No existe un cliente con Id: " + id + nl);
	}

	public void editarCredito(Scanner consola) {
	  if(!mostrarClientes()){
		  logger.info("Debe agregar un cliente primero" + nl);
		  return;
	  }
	  // crear un metodo para reutilizar estas lineas
		logger.info("Id del cliente: ");
		var id = Integer.parseInt(consola.nextLine());
		var cliente = clienteServ.buscarCliente(id);

		if(cliente != null){
			logger.info(nl +"Creditos de " + cliente.getNombre() + "-----v" + nl);
			mostrarCreditos(cliente);
			logger.info("Seleccione un credito: ");
			var idCredito = Integer.parseInt(consola.nextLine());
			var credito = clienteServ.buscarCredito(idCredito,cliente);
			if(credito != null){
				logger.info(nl + "Editar credito----->" +nl);
				logger.info(credito.toString() + nl);
				rellenarCredito(credito,consola);
				creditoServ.agregarCredito(credito);
				logger.info("Cambios guardados!" + nl);
			}
			else logger.info("No existe un credito con Id: " + idCredito);
		}

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
		logger.info(nl + "Creditos de " + cliente.getNombre() + " -----v" +nl);
		creditos.forEach(credito -> logger.info(credito.toString() + nl));
		logger.info("Seleccione un credito (Id): ");
		var idCredito = Integer.parseInt(consola.nextLine());
		Credito credito = clienteServ.buscarCredito(idCredito,cliente);
		if(credito == null){
			logger.info("No existe un credito con Id: " + idCredito + nl);
			return;
		}

		logger.info(nl + "Efectuar pago----->" + nl);
		logger.info(credito.toString() + " seleccionado" + nl);
		logger.info("Ingrese el valor a cancelar: ");
		var valor = BigDecimal.valueOf(Double.parseDouble(consola.nextLine()));

		var pago  = new Pago();
		pago.setCliente(cliente);
		pago.setCredito(credito);
		pago.setValor(valor);
		pagoServ.agregarPago(pago);
		logger.info("Pago registrado!" + nl);

		credito.getPagos().add(pago);
		cliente.getPagos().add(pago);

		logger.info("Pagos-----v" + nl);
		clienteServ.listarPagos(cliente.getIdCliente()).forEach(obj -> {
			if(obj.getCredito().getIdCredito() == idCredito){
				logger.info(obj.toString() + nl);
			}

		});
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

	public void mostrarCreditos(Cliente cliente){
		cliente.getCreditos().forEach(c->logger.info(c.toString()+nl));
	}

	public void rellenarCredito(Credito credito, Scanner consola) {
		logger.info("Monto: ");
		var monto = new BigDecimal(consola.nextLine());
		logger.info("Tasa de interés: ");
		var ti = new BigDecimal(consola.nextLine());
		var fechaI = LocalDate.now();
		logger.info("Fecha final (yy-mm-dd): ");
		var fecha = consola.nextLine();
		var formateador = DateTimeFormatter.ofPattern("yy-MM-dd");
		var fechaF = LocalDate.parse(fecha,formateador);
		BigDecimal valorT = monto.add(monto.multiply(ti));
		BigDecimal saldo;

		if(credito.getIdCredito() != null){
			if(credito.getPagos().isEmpty()){
				monto = monto.add(credito.getMonto());
				valorT = valorT.add(credito.getSaldo());
				saldo = valorT;
			}else {
				valorT = valorT.add(credito.getValorTotal());
				actualizarPagos(credito.getPagos(),valorT);
				monto = monto.add(credito.getMonto());
				saldo = credito.getPagos().getLast().getSaldo();
			}
		}
		else saldo = valorT;

		credito.setMonto(monto);
		credito.setTi(ti);
		credito.setFechaI(fechaI);
		credito.setValorTotal(valorT);
		credito.setSaldo(saldo);
		credito.setFechaF(fechaF);
	}

	public void actualizarPagos(List<Pago> pagos, BigDecimal totalPrestado){
		var saldoAct = totalPrestado.subtract(pagos.getFirst().getValor());
		pagos.getFirst().setSaldo(saldoAct);
		for(Pago p : pagos){
			if(pagos.indexOf(p) == 0) continue;
			saldoAct = saldoAct.subtract(p.getValor());
			p.setSaldo(saldoAct);
		}
	}

}
