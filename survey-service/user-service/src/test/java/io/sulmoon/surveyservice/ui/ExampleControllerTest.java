package io.sulmoon.surveyservice.ui;

import io.restassured.RestAssured;
import io.sulmoon.surveyservice.dto.request.CreateExampleRequestDto;
import io.sulmoon.surveyservice.dto.request.CreateQuestionRequestDto;
import io.sulmoon.surveyservice.dto.request.UpdateExampleRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExampleControllerTest {

    @LocalServerPort
    int port;

    String exampleContent = "E1";

    Long userId = 1L;
    String questionContent = "Q1";
    Boolean subjectiveYn = true;
    Boolean multipleSelectionYn = false;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = this.port;
    }

    @Test
    @Order(3)
    @DisplayName("보기 조회")
    void 보기_조회() {
        createSurvey(1L);
        createQuestion(1L);
        createExample(1L);
        when()
                .get("/api/examples/{exampleId}", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("exampleContent", equalTo(exampleContent))
                .log().all();
    }

    @Test
    @Order(5)
    @DisplayName("보기 삭제")
    void 보기_삭제() {
        createSurvey(1L);
        createQuestion(1L);
        createExample(1L);
        when()
                .delete("/api/examples/{exampleId}", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .log().all();
    }

    @Test
    @Order(4)
    @DisplayName("보기 수정")
    void 보기_수정() {
        UpdateExampleRequestDto requestData = UpdateExampleRequestDto.builder()
                .exampleContent("수정된 보기")
                .build();
        createSurvey(1L);
        createExample(1L);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestData)
        .when()
                .put("/api/examples/{exampleId}", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("exampleContent", equalTo(requestData.getExampleContent()))
                .log().all();
    }

    @Test
    @Order(2)
    @DisplayName("보기 생성")
    void 보기_생성() {
        createSurvey(1L);
        createQuestion(1L);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateExampleRequestDto(exampleContent))
        .when()
                .post("/api/questions/{questionId}/examples",1)
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("exampleContent", equalTo(exampleContent))
                .log().all();
    }

    @Test
    @Order(10)
    @DisplayName("보기 목록 조회")
    void 보기_목록_조회() {
        createSurvey(1L);
        createQuestion(1L);
        createExample(1L);
        createExample(1L);
        createExample(2L);
        when()
                .get("/api/questions/{questionId}/examples", 2)
        .then()
                .statusCode(HttpStatus.OK.value())
                .log().all();
    }

    private void createSurvey(Long userId) {
        given()
                .post("/api/users/{userId}/surveys", userId)
                .then()
                .log().all();
    }

    private void createQuestion(Long surveyId) {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateQuestionRequestDto(
                        userId, questionContent, subjectiveYn, multipleSelectionYn))
                .post("/api/surveys/{surveyId}/questions", surveyId)
        .then()
                .log().all();
    }

    private void createExample(Long questionId) {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateExampleRequestDto(exampleContent))
                .post("/api/questions/{questionId}/examples",questionId)
        .then()
                .log().all();
    }

}