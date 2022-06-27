package com.vr.miniautorizador.controller;

import com.vr.miniautorizador.domain.cartao.Cartao;
import com.vr.miniautorizador.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<Cartao> criarCartao(@RequestBody Cartao cartao) throws Exception {
        return ResponseEntity.ok().body(this.cartaoService.save(cartao).get());
    }

    @GetMapping(value = "/{numeroCartao}")
    public ResponseEntity<BigDecimal> recuperarSaldoCartao(@PathVariable String numeroCartao) throws Exception {
        return ResponseEntity.ok().body(this.cartaoService.getSaldoCartao(numeroCartao).getSaldo());
    }

}
