package com.natera.triangle.service;


import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class TrianglesTest extends TriangleApi {
    String triangleId;

    @Test
    public void addTriangleTest() {
        ValidatableResponse newTriangleResponse = addTriangle(1, 2, 3, ";");
        triangleId = getTriangleValue(newTriangleResponse, "id");
        newTriangleResponse
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(triangleId))
                .body("firstSide", equalTo(1.0f))
                .body("secondSide", equalTo(2.0f))
                .body("thirdSide", equalTo(3.0f));
    }

    @Test
    public void addEqualSidesTriangleTest() {
        ValidatableResponse newTriangleResponse = addTriangle(1, 1, 1, ";");
        triangleId = getTriangleValue(newTriangleResponse, "id");
        newTriangleResponse
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(triangleId))
                .body("firstSide", equalTo(1.0f))
                .body("secondSide", equalTo(1.0f))
                .body("thirdSide", equalTo(1.0f));
    }

    @Test
    public void addTriangleIsActuallyLine() {
        ValidatableResponse responseShouldHave = addTriangle(2, 3, 5, ";");
        responseShouldHave
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("id", equalTo(triangleId))
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Cannot process input"));
    }

    @Test
    public void addImpossbileTriangle() {
        ValidatableResponse responseShouldHave = addTriangle(1, 3, 7, ";");
        responseShouldHave
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("id", equalTo(triangleId))
                .body("error", equalTo("Unprocessable Entity"))
                .body("message", equalTo("Cannot process input"));
    }

    @Test
    public void addNegativeSideTriangleTest() {
        ValidatableResponse newTriangleResponse = addTriangle(-1, 1, 1, ";");
        triangleId = getTriangleValue(newTriangleResponse, "id");
        newTriangleResponse
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(triangleId))
                .body("firstSide", equalTo(-1.0f))
                .body("secondSide", equalTo(1.0f))
                .body("thirdSide", equalTo(1.0f));
    }

    @Test
    public void addZeroSidesTriangleTest() {
        ValidatableResponse newTriangleResponse = addTriangle(0, 0, 0, ";");
        triangleId = getTriangleValue(newTriangleResponse, "id");
        newTriangleResponse
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(triangleId))
                .body("firstSide", equalTo(0.0f))
                .body("secondSide", equalTo(0.0f))
                .body("thirdSide", equalTo(0.0f));
    }

    @Test
    public void limitOfTrianglesTest() {
        deleteAllTrianglesById();
        addTrianglesUntilLimit(10, 0).statusCode(HttpStatus.SC_OK);
        addTrianglesUntilLimit(10, 1).statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
        Assert.assertThat(getAllTriangleValues(getAllTriangles(), "id"), hasSize(10));
    }
}