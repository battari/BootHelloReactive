package org.example.boothello.exceptions;

import org.example.boothello.bean.Welcome;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String from) {
        super(from +" is not found.");
    }
}

