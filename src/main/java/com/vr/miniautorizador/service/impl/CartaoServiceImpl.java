package com.vr.miniautorizador.service.impl;

import com.vr.miniautorizador.domain.cartao.Cartao;
import com.vr.miniautorizador.domain.transacao.TransacaoDTO;
import com.vr.miniautorizador.erros.MiniAutorizadorException;
import com.vr.miniautorizador.repository.CartaoRepository;
import com.vr.miniautorizador.service.CartaoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
public class CartaoServiceImpl implements CartaoService{

    private CartaoRepository cartaoRepository;

    public CartaoServiceImpl(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Override
    public Optional<Cartao> save(Cartao cartao) throws ResponseStatusException {
        if (!this.cartaoRepository.existsCartaoByNumeroCartao(cartao.getNumeroCartao())){
            return Optional.of(this.cartaoRepository.save(cartao));
        }else {
            throw new MiniAutorizadorException("CARTAO JÁ CADASTRADO", cartao, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public Cartao getSaldoCartao(String numeroCartao) throws Exception {
        if(!isExistsCartao(numeroCartao)){
            throw new MiniAutorizadorException("CARTAO NÃO ENCONTRADO", new Cartao(), HttpStatus.NOT_FOUND);
        }else{
            return this.cartaoRepository.findCartaoByNumeroCartao(numeroCartao);
        }
    }

    @Override
    public Cartao evetivarTransacao(TransacaoDTO transacao){
        Cartao cartao = this.cartaoRepository.findCartaoByNumeroCartao(transacao.getNumeroCartao());
        validadeCartao(transacao, cartao);
        cartao.setSaldo(cartao.getSaldo().subtract(transacao.getValor()));
        return this.cartaoRepository.save(cartao);
    }

    private void validadeCartao(TransacaoDTO transacao, Cartao cartao) {
        if(!isExistsCartao(transacao.getNumeroCartao())){
            throw new MiniAutorizadorException("CARTAO_INEXISTENTE", new Cartao(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if(cartao.getSaldo().compareTo(transacao.getValor()) < 0){
            throw new MiniAutorizadorException("SALDO_INSUFICIENTE", cartao, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if(!Objects.equals(transacao.getSenhaCartao(), cartao.getSenha())){
            throw new MiniAutorizadorException("SENHA_INVÁLIDA", cartao, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private boolean isExistsCartao(String numeroCartao) {
        return this.cartaoRepository.existsCartaoByNumeroCartao(numeroCartao);
    }
}
