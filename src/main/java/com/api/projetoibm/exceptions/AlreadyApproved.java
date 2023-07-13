package com.api.projetoibm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AlreadyApproved extends RuntimeException {
    public AlreadyApproved(String name) {
        super("O candidato " + name + " já está aprovado!");
    }
}
