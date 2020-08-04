package com.natera.triangle.service;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Before;

import java.util.List;

import static com.natera.triangle.service.enums.Parameters.*;
import static com.natera.triangle.service.enums.Paths.ALL;
import static com.natera.triangle.service.enums.Paths.NATERA_URI;
import static io.restassured.RestAssured.given;

public class TriangleApi {
    @Before
    public void clear() {
        deleteAllTrianglesById();
    }

    public static RequestSpecification reqSpecification() {
        return given()
                .baseUri(NATERA_URI.path)
                .header(X_USER.value, TOKEN.value).contentType(ContentType.JSON);
    }

    private static String triangleParameters(int sideA, int sideB, int sideC, String separator) {
        JSONObject body = new JSONObject();
        body.put(INPUT.value, sideA + separator + sideB + separator + sideC);
        if (separator != null) {
            body.put(SEPARATOR.value, separator);
        }
        return body.toString();
    }

    public static ValidatableResponse getAllTriangles() {
        return reqSpecification().get(ALL.path).prettyPeek().then().log().all();
    }

    public static ValidatableResponse getTriangleById(String id) {
        return reqSpecification().get("/" + id).prettyPeek().then().log().all();
    }

    public static String getTriangleValue(ValidatableResponse response, String key) {
        String triangleValue = response.extract().path(key).toString();
        return triangleValue;
    }

    public static List<String> getAllTriangleValues(ValidatableResponse response, String key) {
        List<String> triangleIds = response
                .extract()
                .jsonPath()
                .getList(key);
        return triangleIds;
    }

    public static ValidatableResponse addTriangle(int sideA, int sideB, int sideC, String separator) {
        return reqSpecification()
                .body(triangleParameters(sideA, sideB, sideC, separator))
                .post()
                .prettyPeek()
                .then()
                .log()
                .all();
    }

    public static void deleteAllTrianglesById() {
        List<String> values = getAllTriangleValues(getAllTriangles(), "id");
        if (values.size() > 0) {
            values.forEach(value -> reqSpecification()
                    .delete(value)
                    .prettyPeek()
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
}

