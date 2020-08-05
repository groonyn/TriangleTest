package com.natera.triangle.service.enums;

public enum Paths {
    ALL("/all"),
    AREA("/area"),
    NATERA_URI("https://qa-quiz.natera.com/triangle"),
    PERIMETER("/perimeter");

    public String path;

    Paths(String path) {
        this.path = path;
    }
}
