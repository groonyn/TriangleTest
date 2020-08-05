package com.natera.triangle.service;

import com.natera.triangle.service.core.TriangleApi;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class ManipulationsTest extends TriangleApi {
    @Test
    public void getTriangleTest() {
        ValidatableResponse triangle;
        triangle = addTriangle(3, 4, 5, ";")
                .statusCode(HttpStatus.SC_OK);
        String triangleId = getTriangleValue(triangle, "id");
        getTriangleById(triangleId)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("firstSide", equalTo(3.0f))
                .body("secondSide", equalTo(4.0f))
                .body("thirdSide", equalTo(5.0f));
    }

    @Test
    public void getTrianglePerimeterTest() {
        ValidatableResponse triangle;
        triangle = addTriangle(3, 4, 5, ";")
                .statusCode(HttpStatus.SC_OK);
        String triangleId;
        triangleId = getTriangleValue(triangle, "id");
        getTrianglePerimeterById(triangleId)
                .statusCode(HttpStatus.SC_OK)
                .body("result", equalTo(12.0f));
    }

    @Test
    public void getTriangleAreaTest() {
        ValidatableResponse triangle;
        triangle = addTriangle(3, 4, 5, ";")
                .statusCode(HttpStatus.SC_OK);
        String triangleId;
        triangleId = getTriangleValue(triangle, "id");
        getTriangleAreaById(triangleId)
                .statusCode(HttpStatus.SC_OK)
                .body("result", equalTo(6.0f));
    }

    @Test
    public void deleteTrianglesTest() {
        ValidatableResponse triangle;
        triangle = addTriangle(3, 4, 5, ";")
                .statusCode(HttpStatus.SC_OK);
        String triangleId;
        triangleId = getTriangleValue(triangle, "id");
        deleteAllTrianglesById();
        getTriangleById(triangleId)
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
