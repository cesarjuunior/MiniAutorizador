package com.vr.miniautorizador.erros;

import com.vr.miniautorizador.domain.cartao.Cartao;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MiniAutorizadorException extends RuntimeException{
    private final Cartao body;
    private final HttpStatus httpStatus;
    public MiniAutorizadorException(String message, Cartao body, HttpStatus httpStatus) {
        super(message);
        this.body = body;
        this.httpStatus = httpStatus;
    }
}
