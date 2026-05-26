package com.docTime.validation;

public enum ErrorType {
    INVALID_INPUT("ERR-1001", "Les données fournies sont invalides."),
    RESOURCE_NOT_FOUND("ERR-2001", "La ressource demandée n'existe pas."),
    BUSINESS_ERROR("ERR-3001", "Une règle de gestion a été violée."),
    SERVER_ERROR("ERR-5001", "Une erreur interne est survenue.");

    private final String code;
    private final String defaultMessage;

    ErrorType(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() { return code; }
    public String getDefaultMessage() { return defaultMessage; }
}
