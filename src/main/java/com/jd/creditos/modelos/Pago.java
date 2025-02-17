package com.jd.creditos.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    // Relación con Credito
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "idCredito", nullable = false)
    private Credito credito;
}
