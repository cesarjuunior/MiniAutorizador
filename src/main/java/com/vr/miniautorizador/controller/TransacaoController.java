package com.vr.miniautorizador.controller;

import com.vr.miniautorizador.domain.cartao.Cartao;
import com.vr.miniautorizador.domain.transacao.TransacaoDTO;
import com.vr.miniautorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<Cartao> efetivarTransacao(@RequestBody TransacaoDTO transacao) throws Exception {
        return ResponseEntity.ok().body(this.cartaoService.evetivarTransacao(transacao));
    }
}
