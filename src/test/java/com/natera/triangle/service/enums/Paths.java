package com.natera.triangle.service.enums;

public enum Paths {
    NATERA_URI("https://qa-quiz.natera.com/triangle"),
    ALL("/all");

    public String path;

    Paths(String path) {
        this.path = path;
    }
}
