package com.jd.creditos.modelos;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="pagos")
public class Pago {
    @Id
    @Column(name = "idPago")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPago;

    @Column(nullable = false)
    private Double valor;

    private LocalDate fecha;

    private Double saldo;

    // Relación con Cliente
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    // Relación con Credito
    @ManyToOne
    @JoinColumn(name = "idCredito", nullable = false)
    private Credito credito;
}
