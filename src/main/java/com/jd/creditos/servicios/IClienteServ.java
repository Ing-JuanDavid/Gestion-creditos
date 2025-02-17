package com.jd.creditos.servicios;

import com.jd.creditos.modelos.Cliente;
import com.jd.creditos.modelos.Credito;
import com.jd.creditos.modelos.Pago;

import java.util.List;

public interface IClienteServ {
    public List<Cliente> listarClientes();

    public void agregarCliente(Cliente cliente);

    public Cliente buscarCliente(Integer idCliente);

    public void eliminarCliente(Integer idCliente);

    public List<Credito> listarCreditos(Integer idcliente);

    public List<Pago> listarPagos(Integer idCliente);
}
