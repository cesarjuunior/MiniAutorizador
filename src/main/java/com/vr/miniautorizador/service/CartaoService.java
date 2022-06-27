package com.vr.miniautorizador.service;

import com.vr.miniautorizador.domain.cartao.Cartao;
import com.vr.miniautorizador.domain.transacao.TransacaoDTO;

import java.util.Optional;

public interface CartaoService {
    Optional<Cartao> save(Cartao cartao) throws Exception;

    Cartao getSaldoCartao(String numeroCartao) throws Exception;

    Cartao evetivarTransacao(TransacaoDTO cartao) throws Exception;


}
