package com.natera.triangle.service.enums;

public enum Parameters {
    X_USER("X-User"),
    TOKEN("9b5955b0-8e4f-4c5f-a2e7-8bc0dbd9e614"),
    INPUT("input"),
    SEPARATOR_CHAR(";"),
    SEPARATOR("separator");

    public String value;

    Parameters(String value) {
        this.value = value;
    }
}
