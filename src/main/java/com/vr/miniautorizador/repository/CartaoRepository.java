package com.vr.miniautorizador.repository;

import com.vr.miniautorizador.domain.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    boolean existsCartaoByNumeroCartao(String numeroCartao);

    Cartao findCartaoByNumeroCartao(String numeroCartao);
}
