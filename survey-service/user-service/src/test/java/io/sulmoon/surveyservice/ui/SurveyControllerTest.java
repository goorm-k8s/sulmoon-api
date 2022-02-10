package io.sulmoon.surveyservice.ui;

import io.restassured.RestAssured;
import io.sulmoon.surveyservice.dto.request.answer.CreateAnswerRequestDto;
import io.sulmoon.surveyservice.dto.request.question.CreateQuestionRequestDto;
import io.sulmoon.surveyservice.dto.request.survey.UpdateSurveyRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SurveyControllerTest {

    @LocalServerPort
    int port;

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
    @Order(4)
    @DisplayName("식별자로 설문 조회")
    void 식별자로_설문_조회() {
        createSurvey(1L);

        when()
                .get("/api/surveys/{surveyId}", 1)
        .then().statusCode(HttpStatus.OK.value())
                .log().all();
    }

    @Test
    @Order(6)
    @DisplayName("식별자로 설문 삭제")
    void 식별자로_설문_삭제() {
        createSurvey(1L);

        when()
                .delete("/api/surveys/{surveyId}", 1)
        .then().statusCode(HttpStatus.OK.value())
                .log().all();
    }

    @Test
    @DisplayName("설문 제목, 설명 수정")
    @Order(5)
    void 설문_제목_설명_수정() {
        UpdateSurveyRequestDto requestData = UpdateSurveyRequestDto.builder()
                .title("제목3")
                .description("설명3")
                .build();

        createSurvey(1L);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestData)
        .when()

                .put("/api/surveys/{surveyId}", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo("제목3"))
                .body("description", equalTo("설명3"))
                .log().all();
    }

    @Test
    @Order(3)
    @DisplayName("설문 생성")
    void 설문_생성() {
        given()
        .when()
                .post("/api/users/{userId}/surveys", 1)
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .log().all();
    }

    @Test
    @Order(2)
    @DisplayName("내가 작성한 설문 목록 조회")
    void 설문_목록_조회() {
        createSurvey(1L);

        given()
        .when()
                .get("/api/users/{userId}/surveys", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .log().all();
    }

    @Test
    @Order(8)
    @DisplayName("내가 답변한 설문 목록 조회")
    void 답변_설문_목록_조회() {
        createSurvey(1L);
        createSurvey(2L);
        createSurvey(3L);
        createQuestion(2L);
        createQuestion(3L);
        createAnswer(2L, 1L);
        createAnswer(3L, 1L);
        given()
        .when()
                .get("/api/users/{userId}/surveys/{surveyId}/answers", 1, 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .log().all();
    }

    private void createAnswer(Long surveyId, Long questionId) {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateAnswerRequestDto(userId, "A1"))
                .post("/api/surveys/{surveyId}/questions/{questionId}/answers", surveyId, questionId)
                .then()
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

}