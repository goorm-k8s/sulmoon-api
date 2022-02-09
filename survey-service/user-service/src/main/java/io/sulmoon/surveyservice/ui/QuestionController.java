package io.sulmoon.surveyservice.ui;

import io.sulmoon.surveyservice.application.QuestionService;
import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.dto.QuestionDto;
import io.sulmoon.surveyservice.dto.request.CreateQuestionRequestDto;
import io.sulmoon.surveyservice.dto.request.UpdateQuestionRequestDto;
import io.sulmoon.surveyservice.dto.response.question.CreateQuestionResponseDto;
import io.sulmoon.surveyservice.dto.response.question.DeleteQuestionResponseDto;
import io.sulmoon.surveyservice.dto.response.question.SearchQuestionResponseDto;
import io.sulmoon.surveyservice.dto.response.question.UpdateQuestionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/questions/{questionId}")
    public ResponseEntity<SearchQuestionResponseDto> searchQuestion(
            @PathVariable Long questionId) {
        Question question = this.questionService.searchQuestion(questionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SearchQuestionResponseDto(question.getId(), question.getQuestionContent(),
                        question.getSubjectiveYn(), question.getMultipleSelectionYn(),
                        question.getSurvey().getId()));
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<DeleteQuestionResponseDto> deleteQuestion(
            @PathVariable Long questionId) {
        Long deleteQuestionId = this.questionService.deleteQuestion(questionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DeleteQuestionResponseDto(deleteQuestionId));
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<UpdateQuestionResponseDto> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody UpdateQuestionRequestDto requestData) {
        QuestionDto questionDto = QuestionDto.builder()
                .id(questionId)
                .questionContent(requestData.getQuestionContent())
                .subjectiveYn(requestData.getSubjectiveYn())
                .multipleSelectionYn(requestData.getMultipleSelectionYn())
                .userid(requestData.getUserId())
                .build();

        Question question = this.questionService.updateQuestion(questionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UpdateQuestionResponseDto(question.getId(), question.getQuestionContent(),
                        question.getSubjectiveYn(), question.getMultipleSelectionYn(),
                        question.getSurvey().getId()));
    }

    @PostMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<CreateQuestionResponseDto> createQuestion(
            @PathVariable Long surveyId,
            @RequestBody CreateQuestionRequestDto requestData) {
        QuestionDto questionDto = QuestionDto.builder()
                .questionContent(requestData.getQuestionContent())
                .subjectiveYn(requestData.getSubjectiveYn())
                .multipleSelectionYn(requestData.getMultipleSelectionYn())
                .userid(requestData.getUserId())
                .build();
        Question question = this.questionService.createQuestion(surveyId, questionDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateQuestionResponseDto(question.getId(), question.getQuestionContent(),
                        question.getSubjectiveYn(), question.getMultipleSelectionYn(),
                        question.getSurvey().getId()));
    }

    @GetMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<List<SearchQuestionResponseDto>> searchQuestionList(
            @PathVariable Long surveyId) {
        List<Question> questions = this.questionService.searchQuestionListBySurvey(surveyId);
        List<SearchQuestionResponseDto> results = new ArrayList<>();
        for (Question question : questions) {
            results.add(new SearchQuestionResponseDto(question.getId(), question.getQuestionContent(),
                    question.getSubjectiveYn(), question.getMultipleSelectionYn(),
                    question.getSurvey().getId()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(results);
    }


}
