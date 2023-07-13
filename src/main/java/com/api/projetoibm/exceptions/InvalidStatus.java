package com.api.projetoibm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidStatus extends RuntimeException {
    public InvalidStatus() {
        super("Este candidato não possui o status necessário para completar a ação!");
    }
}