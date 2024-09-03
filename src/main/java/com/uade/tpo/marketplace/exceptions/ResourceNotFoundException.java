package com.uade.tpo.marketplace.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private String message;
}
