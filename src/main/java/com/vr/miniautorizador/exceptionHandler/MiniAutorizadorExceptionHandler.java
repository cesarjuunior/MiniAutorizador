package com.vr.miniautorizador.exceptionHandler;

import com.vr.miniautorizador.erros.ExceptionBody;
import com.vr.miniautorizador.erros.MiniAutorizadorException;
import com.vr.miniautorizador.erros.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MiniAutorizadorExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MiniAutorizadorException.class)
    public ResponseEntity<?> handleException(MiniAutorizadorException miniAutorizadorException){

        ExceptionDetails exceptionDetails = ExceptionDetails.ExceptionDetailsBuilder.newBuilder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .title(miniAutorizadorException.getMessage())
                .body(new ExceptionBody(miniAutorizadorException.getBody().getNumeroCartao(), miniAutorizadorException.getBody().getSenha()))
                .build();

        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNPROCESSABLE_ENTITY );
    }
}

