package com.jd.creditos.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="creditos")
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCredito;

    @Column(nullable = false, precision = 13, scale = 3)
    private BigDecimal monto;

    private LocalDate fechaI;

    @Column(name = "plazo", nullable = false)
    private LocalDate fechaF;

    @Column(nullable = false, precision = 2, scale = 2)
    private BigDecimal ti;

    @Column(precision = 13, scale = 3)
    private BigDecimal valorTotal;

    @Column(precision = 13, scale = 3)
    private BigDecimal saldo;

    // Referencia a cliente
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    // Relacion de uno a muchos con pago
    @OneToMany(mappedBy = "credito", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Para evitar problemas de Lazy Loading
    private List<Pago> pagos;
}
