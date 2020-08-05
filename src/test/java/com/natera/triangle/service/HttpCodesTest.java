package com.natera.triangle.service;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static com.natera.triangle.service.core.TriangleApi.*;
import static com.natera.triangle.service.enums.Parameters.X_USER;

public class HttpCodesTest {
    @Test
    public void codeOKTest() {
        getAllTriangles()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void codeUnauthorizedTest() {
        reqSpecificationWithParameters(X_USER.value, "bad token")
                .body("")
                .post()
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

    }

    @Test
    public void codeNotFoundTest() {
        reqSpecification()
                .get("/none")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void codeUnprocessableEntityTest() {
        addTriangle(3, 4, 5, "wrong")
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    public void codeMethodNotAllowedTest() {
        reqSpecification()
                .post("/none")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    public void codeNotAcceptableTest() {
        reqSpecification()
                .accept(ContentType.XML)
                .post()
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_NOT_ACCEPTABLE);
    }
}
