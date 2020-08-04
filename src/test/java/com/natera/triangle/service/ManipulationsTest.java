package com.natera.triangle.service;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

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

//    @Test
//    public void getTriangleAreaTest() {
//        String testId = getTestTriangleId(3, 4, 5);
//        getTriangleArea(testId)
//                .statusCode(200)
//                .body("result", equalTo(6.0f));
//    }
//
//    @Test
//    public void getTrianglePerimeterTest() {
//        String testId = getTestTriangleId(3, 4, 5);
//        getTrianglePerimeter(testId)
//                .statusCode(200)
//                .body("result", equalTo(12.0f));
//    }
//
//    @Test
//    public void deleteTriangleTest() {
//        String testId = getTestTriangleId();
//        deleteTriangle(testId).statusCode(200);
//        getTriangle(testId).statusCode(404);
//    }
//
//    @Test
//    public void listTrianglesTest() {
//        String testId = getTestTriangleId();
//        ValidatableResponse response = listTriangles();
//        response.statusCode(200);
//        Assert.assertTrue(response.extract().asString().contains(testId));
//    }

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
