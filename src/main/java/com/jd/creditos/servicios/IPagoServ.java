package com.jd.creditos.servicios;

import com.jd.creditos.modelos.Credito;
import com.jd.creditos.modelos.Pago;

import java.util.List;

public interface IPagoServ {
    public List<Pago> listarPagos();

    public void agregarPago(Pago pago);

    public Pago buscarPago(Integer idPago);

    public void eliminarPago(Integer idPago);
}
