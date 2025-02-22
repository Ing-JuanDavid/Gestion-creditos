package com.jd.creditos.servicios.imp;

import com.jd.creditos.modelos.Cliente;
import com.jd.creditos.modelos.Credito;
import com.jd.creditos.modelos.Pago;
import com.jd.creditos.repositorios.IClienteRepo;
import com.jd.creditos.servicios.IClienteServ;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServImp implements IClienteServ {
    @Autowired
    private IClienteRepo clienteRepo;

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepo.findAll();
    }

    @Override
    public void agregarCliente(Cliente cliente) {
        clienteRepo.save(cliente);
    }

    @Override
    public Cliente buscarCliente(Integer idCliente) {
        return clienteRepo.findById(idCliente).orElse(null);
    }

    @Override
    public void eliminarCliente(Integer idCliente) {
        clienteRepo.deleteById(idCliente);
    }


    @Override
    public List<Credito> listarCreditos(Integer idCliente) {
        return clienteRepo.findById(idCliente)
                          .map(Cliente::getCreditos)
                          .orElse(List.of());
    }

    @Override
    public List<Pago> listarPagos(Integer idCliente) {
        return clienteRepo.findById(idCliente)
                          .map(Cliente::getPagos)
                          .orElse(List.of());
    }
}
