package com.jd.creditos.servicios.imp;

import com.jd.creditos.modelos.Credito;
import com.jd.creditos.modelos.Pago;
import com.jd.creditos.repositorios.ICreditoRepo;
import com.jd.creditos.servicios.ICreditoServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditoServImp implements ICreditoServ {
    @Autowired
    private ICreditoRepo creditoRepo;

    @Override
    public List<Credito> listarCreditos() {
        return creditoRepo.findAll();
    }

    @Override
    public void agregarCredito(Credito credito) {
        creditoRepo.save(credito);
    }

    @Override
    public Credito buscarCredito(Integer idCredito) {
        return creditoRepo.findById(idCredito).orElse(null);
    }

    @Override
    public void eliminarCredito(Integer idCredito) {
        creditoRepo.deleteById(idCredito);
    }

    @Override
    public List<Pago> listarPagos(Integer idCredito) {
        return creditoRepo.findById(idCredito)
                          .map(Credito::getPagos)
                          .orElse(List.of());
    }

}
