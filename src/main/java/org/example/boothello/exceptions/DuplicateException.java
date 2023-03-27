package org.example.boothello.exceptions;

import org.example.boothello.bean.Welcome;
import org.springframework.http.HttpStatus;

public class DuplicateException extends RuntimeException {
    public DuplicateException(Welcome welcome) {
        super(welcome + " already exists.");
    }
}
