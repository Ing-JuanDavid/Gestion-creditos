package com.jd.creditos.servicios.imp;

import com.jd.creditos.modelos.Pago;
import com.jd.creditos.repositorios.IPagoRepo;
import com.jd.creditos.servicios.IPagoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServImp implements IPagoServ {

    @Autowired
    private IPagoRepo pagoRepo;

    @Override
    public List<Pago> listarPagos() {
        return pagoRepo.findAll();
    }

    @Override
    public void agregarPago(Pago pago) {
        pagoRepo.save(pago);
    }

    @Override
    public Pago buscarPago(Integer idPago) {
        return pagoRepo.findById(idPago).orElse(null);
    }

    @Override
    public void eliminarPago(Integer idPago) {
        pagoRepo.deleteById(idPago);
    }
}
