package com.jd.creditos.modelos;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    @Column(nullable = false, precision = 13, scale = 3)
    private BigDecimal valor;

    private LocalDate fecha;

    @Column(precision = 13, scale = 3)
    private BigDecimal saldo;

    // Relación con Cliente
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    // Relación con Credito
    @ManyToOne
    @JoinColumn(name = "idCredito", nullable = false)
    private Credito credito;
}
