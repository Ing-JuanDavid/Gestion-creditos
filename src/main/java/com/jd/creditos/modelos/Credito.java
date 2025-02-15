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
    @Column(name="idCredito")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCredito;
    @Column(nullable = false)
    private Double monto;
    @Column(name="fechaI")
    private LocalDate fechaI;

    @Column(name = "plazo", nullable = false)
    private LocalDate fechaF;

    @Column(nullable = false)
    private Double ti;

    @Column(name="valorTotal")
    private Double valorTotal;

    private Double saldo;

    // Referencia a cliente
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    // Relacion de uno a muchos con pago
    @OneToMany(mappedBy = "credito", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Para evitar problemas de Lazy Loading
    private List<Pago> pagos;
}
