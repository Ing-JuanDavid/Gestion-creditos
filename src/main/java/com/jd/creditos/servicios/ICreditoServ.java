package com.jd.creditos.servicios;
import com.jd.creditos.modelos.Credito;
import com.jd.creditos.modelos.Pago;

import java.util.List;

public interface ICreditoServ {
    public List<Credito> listarCreditos();

    public void agregarCredito(Credito credito);

    public Credito buscarCredito(Integer idCredito);

    public void eliminarCredito(Integer idCredito);

    public List<Pago> listarPagos(Integer idCredito);
}
