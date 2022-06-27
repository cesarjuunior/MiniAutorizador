package com.vr.miniautorizador.erros;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExceptionBody {
    private String numeroCartao;
    private String senha;

    public ExceptionBody(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }
}
