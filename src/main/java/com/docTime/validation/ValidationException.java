package com.docTime.validation;

public class ValidationException extends RuntimeException {

    private final ErrorType type;

    public ValidationException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

    public ValidationException(ErrorType type) {
        super(type.getDefaultMessage());
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}
