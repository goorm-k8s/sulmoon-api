package io.sulmoon.surveyservice.ui;

import io.restassured.RestAssured;
import io.sulmoon.surveyservice.dto.request.answer.CreateAnswerRequestDto;
import io.sulmoon.surveyservice.dto.request.question.CreateQuestionRequestDto;
import io.sulmoon.surveyservice.dto.request.answer.UpdateAnswerRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnswerControllerTest {

    @LocalServerPort
    int port;

    Long userId = 1L;
    String questionContent = "Q1";
    Boolean subjectiveYn = true;
    Boolean multipleSelectionYn = false;
    String answerContent = "A1";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = this.port;
    }

    @Test
    @Order(2)
    @DisplayName("답변 조회")
    void 답변_조회() {
        createAnswer(1L, 1L);

        when()
                .get("/api/answers/{answerId}", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("answerContent", equalTo(answerContent))
                .log().all();
    }

    @Test
    @Order(10)
    @DisplayName("답변 삭제")
    void 답변_삭제() {
        createAnswer(1L, 1L);

        when()
                .delete("/api/answers/{answerId}", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .log().all();
    }

    @Test
    @Order(3)
    @DisplayName("답변 수정")
    void 답변_수정() {
        UpdateAnswerRequestDto requestData = UpdateAnswerRequestDto.builder()
                .answerContent("수정된 답변")
                .build();

        createSurvey(1L);
        createQuestion(1L);
        createAnswer(1L, 1L);

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestData)
        .when()
                .put("/api/answers/{answerId}", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("answerContent", equalTo(requestData.getAnswerContent()))
                .log().all();
    }

    @Test
    @Order(1)
    @DisplayName("답변 생성")
    void 답변_생성() {
        createSurvey(1L);
        createQuestion(1L);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateAnswerRequestDto(userId, "A1"))
        .when()
                .post("/api/surveys/{surveyId}/questions/{questionId}/answers", 1, 1)
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("answerContent", equalTo(answerContent))
                .log().all();
    }

    @Test
    @Order(11)
    @DisplayName("질문 식별자로 답변 목록 조회")
    void 답변_목록_조회() {
        createSurvey(1L);
        createQuestion(1L);
        createQuestion(1L);
        createAnswer(1L, 1L);
        createAnswer(1L, 1L);
        createAnswer(1L, 2L);
        createAnswer(1L, 2L);
        given()
        .when()
                .get("/api/surveys/{surveyId}/questions/{questionId}/answers", 1, 1)
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

    private void createAnswer(Long surveyId, Long questionId) {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateAnswerRequestDto(userId, "A1"))
                .post("/api/surveys/{surveyId}/questions/{questionId}/answers", surveyId, questionId)
        .then()
                .log().all();
    }
}
