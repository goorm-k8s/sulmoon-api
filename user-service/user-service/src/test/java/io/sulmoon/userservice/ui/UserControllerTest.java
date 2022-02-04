package io.sulmoon.userservice.ui;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.sulmoon.userservice.dto.request.CreateUserRequestDTO;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @LocalServerPort
    int port;

    Long userId = 1L;
    String email = "goorm@goorm.com";
    String providerId = "naver_2d34jk23";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = this.port;
    }

    @Test
    @Order(1)
    void 유저_생성() throws JSONException {
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new CreateUserRequestDTO(email, providerId))
        .when()
            .post("/api/users")
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", equalTo(1))
            .log().all();
    }

    @Test
    @Order(2)
    void 유저명으로_조회() {
        createUser();
        given()
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .get("/api/users/{userId}", userId)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(1))
            .log().all();
    }

    @Test
    @Order(5)
    void 유저를_삭제한다() {
        createUser();
        given()
            .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .delete("/api/users/{userId}", userId)
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", equalTo(1))
            .log().all();
    }

    private void createUser() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateUserRequestDTO(email, providerId))
                .post("/api/users")
        .then()
                .log().all();
    }
}
