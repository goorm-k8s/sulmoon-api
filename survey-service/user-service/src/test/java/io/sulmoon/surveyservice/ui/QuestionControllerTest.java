package io.sulmoon.surveyservice.ui;

import io.restassured.RestAssured;
import io.sulmoon.surveyservice.dto.request.CreateQuestionRequestDto;
import io.sulmoon.surveyservice.dto.request.UpdateQuestionRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuestionControllerTest {

    @LocalServerPort
    int port;

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
    @DisplayName("식별자로 질문 조회")
    void 식별자로_질문_조회() {
        createSurvey(1L);
        createQuestion(1L);
        when()
                .get("/api/questions/{questionId}", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("questionContent", equalTo(questionContent))
                .body("subjectiveYn", equalTo(subjectiveYn))
                .body("multipleSelectionYn", equalTo(multipleSelectionYn))
                .log().all();
    }

    @Test
    @Order(10)
    @DisplayName("질문 삭제")
    void 질문_삭제() {
        createSurvey(1L);
        createQuestion(1L);
        when()
                .delete("/api/questions/{questionId}", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .log().all();
    }

    @Test
    @Order(4)
    @DisplayName("질문 수정")
    void 질문_수정() {
        UpdateQuestionRequestDto requestData = UpdateQuestionRequestDto.builder()
                .questionContent("수정된 질문")
                .subjectiveYn(false)
                .multipleSelectionYn(true)
                .build();
        createSurvey(1L);
        createQuestion(1L);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(requestData)
        .when()
                .put("/api/questions/{questionId}", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("questionContent", equalTo(requestData.getQuestionContent()))
                .body("subjectiveYn", equalTo(requestData.getSubjectiveYn()))
                .body("multipleSelectionYn", equalTo(requestData.getMultipleSelectionYn()))
                .log().all();
    }

    @Test
    @Order(2)
    @DisplayName("질문 생성")
    void 질문_생성() {
        createSurvey(1L);
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CreateQuestionRequestDto(
                        questionContent, subjectiveYn, multipleSelectionYn))
        .when()
                .post("/api/surveys/{surveyId}/questions", 1)
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("questionContent", equalTo(questionContent))
                .body("subjectiveYn", equalTo(subjectiveYn))
                .body("multipleSelectionYn", equalTo(multipleSelectionYn))
                .log().all();
    }

    @Test
    @Order(1)
    @DisplayName("설문 식별자로 질문 리스트 조회")
    void 질문_목록_조회() {
        createSurvey(1L);
        createQuestion(1L);
        createQuestion(1L);
        when()
                .get("/api/surveys/{surveyId}/questions", 1)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", hasItems(1, 2))
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
                        questionContent, subjectiveYn, multipleSelectionYn))
                .post("/api/surveys/{surveyId}/questions", surveyId)
                .then()
                .log().all();
    }
}
