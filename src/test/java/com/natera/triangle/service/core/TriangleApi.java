package com.natera.triangle.service.core;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;

import java.util.List;

import static com.natera.triangle.service.enums.Parameters.*;
import static com.natera.triangle.service.enums.Paths.*;
import static io.restassured.RestAssured.given;

public class TriangleApi {
    @BeforeTest
    public void clear() {
        deleteAllTrianglesById();
    }

    public static RequestSpecification reqSpecificationWithParameters(String headerName, String headerValue) {
        return given()
                .baseUri(NATERA_URI.path)
                .header(headerName, headerValue).contentType(ContentType.JSON);
    }

    public static RequestSpecification reqSpecification() {
        return reqSpecificationWithParameters(X_USER.value, TOKEN.value);
    }

    public static ValidatableResponse getAllTriangles() {
        return reqSpecification().get(ALL.path).then().log().all();
    }

    public static ValidatableResponse getTriangleById(String id) {
        return reqSpecification().get("/" + id).then().log().all();
    }

    public static ValidatableResponse getTrianglePerimeterById(String id) {
        return reqSpecification().get("/" + id + PERIMETER.path).then().log().all();
    }

    public static ValidatableResponse getTriangleAreaById(String id) {

        return reqSpecification().get("/" + id + AREA.path).then().log().all();
    }

    public static String getTriangleValue(ValidatableResponse response, String key) {
        String triangleValue = response.extract().path(key).toString();
        return triangleValue;
    }

    public static ValidatableResponse addTriangle(int sideA, int sideB, int sideC, String separator) {
        return reqSpecification()
                .body(triangleParameters(sideA, sideB, sideC, separator))
                .post()
                .then()
                .log()
                .all();
    }

    private static String triangleParameters(int sideA, int sideB, int sideC, String separator) {
        JSONObject body = new JSONObject();
        body.put(INPUT.value, sideA + SEPARATOR_CHAR.value + sideB + SEPARATOR_CHAR.value + sideC);
        if (separator != null) {
            body.put(SEPARATOR.value, separator);
        }
        return body.toString();
    }

    public static void deleteAllTrianglesById() {
        List<String> values = getAllTriangleValues(getAllTriangles(), "id");
        if (values.size() > 0) {
            values.forEach(value -> reqSpecification()
                    .delete(value)
                    .then()
                    .log()
                    .all());
        }
    }

    public static ValidatableResponse addTrianglesUntilLimit(int limitValue, int overLimitValue) {
        List<String> values = getAllTriangleValues(getAllTriangles(), "id");
        int summaryLimit = 0;
        if (overLimitValue >= 0) {
            summaryLimit = limitValue + overLimitValue - values.size();
        }
        for (int i = 0; i < summaryLimit; i++) {
            addTriangle(3, 4, 5, ";");
        }
        return addTriangle(3, 4, 5, ";");
    }

    public static List<String> getAllTriangleValues(ValidatableResponse response, String key) {
        List<String> triangleIds = response
                .extract()
                .jsonPath()
                .getList(key);
        return triangleIds;
    }
}

