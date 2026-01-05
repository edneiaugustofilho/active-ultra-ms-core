package br.com.activeultra.core.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class FieldValidationException extends RuntimeException {

    private final List<String> errors;

    public FieldValidationException(List<String> errors) {
        super(String.join("; ", errors));
        this.errors = List.copyOf(errors);
    }
}
