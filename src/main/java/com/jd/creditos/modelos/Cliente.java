package com.jd.creditos.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "clientes")
public class Cliente {
    @Id
    private Integer idCliente;
    @Column(nullable = false)
    private String nombre;
    private String estado;

    // Relacion de uno a muchos con credito
    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Credito> creditos;

    // Relacion de uno a muchos con pago
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Para evitar problemas de Lazy Loading
    private List<Pago> pagos;
}
