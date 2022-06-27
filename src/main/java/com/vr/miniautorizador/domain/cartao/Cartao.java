package com.vr.miniautorizador.domain.cartao;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter @Setter
@EqualsAndHashCode
@Entity
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cartao")
    private String numeroCartao;

    private String senha;

    private BigDecimal saldo = new BigDecimal(500);

    @Transient
    private BigDecimal valor;
}
