package io.sulmoon.surveyservice.ui;

import io.sulmoon.surveyservice.application.AnswerService;
import io.sulmoon.surveyservice.domain.Answer;
import io.sulmoon.surveyservice.dto.AnswerDto;
import io.sulmoon.surveyservice.dto.request.answer.CreateAnswerRequestDto;
import io.sulmoon.surveyservice.dto.request.answer.UpdateAnswerRequestDto;
import io.sulmoon.surveyservice.dto.response.answer.CreateAnswerResponseDto;
import io.sulmoon.surveyservice.dto.response.answer.DeleteAnswerResponseDto;
import io.sulmoon.surveyservice.dto.response.answer.SearchAnswerResponseDto;
import io.sulmoon.surveyservice.dto.response.answer.UpdateAnswerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("/answers/{answerId}")
    public ResponseEntity<SearchAnswerResponseDto> searchAnswer(
            @PathVariable Long answerId
    ) {
        Answer answer = this.answerService.searchAnswer(answerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SearchAnswerResponseDto(answer.getId(), answer.getAnswerContent(),
                        answer.getSurvey().getId(), answer.getQuestion().getId(),
                        answer.getUserId()));
    }

    @DeleteMapping("/answers/{answerId}")
    public ResponseEntity<DeleteAnswerResponseDto> deleteAnswer(
            @PathVariable Long answerId) {
        Long deletedAnswerId = this.answerService.deleteAnswer(answerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DeleteAnswerResponseDto(deletedAnswerId));
    }

    @PutMapping("/answers/{answerId}")
    public ResponseEntity<UpdateAnswerResponseDto> updateAnswer(
            @PathVariable Long answerId,
            @RequestBody UpdateAnswerRequestDto requestData) {
        Answer answer = this.answerService.updateAnswer(AnswerDto.builder()
                .id(answerId)
                .answerContent(requestData.getAnswerContent())
                .userId(requestData.getUserId())
                .build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UpdateAnswerResponseDto(answer.getId(), answer.getAnswerContent(),
                        answer.getSurvey().getId(), answer.getQuestion().getId(),
                        answer.getUserId()));
    }

    @PostMapping("/surveys/{surveyId}/questions/{questionId}/answers")
    public ResponseEntity<CreateAnswerResponseDto> createAnswer(
            @PathVariable Long surveyId,
            @PathVariable Long questionId,
            @RequestBody CreateAnswerRequestDto requestData) {

        AnswerDto answerDto = AnswerDto.builder()
                .userId(requestData.getUserId())
                .answerContent(requestData.getAnswerContent())
                .build();
        Answer answer = this.answerService.createAnswer(surveyId, questionId, answerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAnswerResponseDto(answer.getId(), answer.getAnswerContent(),
                        answer.getSurvey().getId(), answer.getQuestion().getId(), answer.getUserId()));
    }

    @GetMapping("/surveys/{surveyId}/questions/{questionId}/answers")
    public ResponseEntity<List<SearchAnswerResponseDto>> searchAnswerListByQuestion(
            @PathVariable Long surveyId,
            @PathVariable Long questionId) {
        List<Answer> answers = this.answerService.searchAnswerListByQuestion(questionId);
        List<SearchAnswerResponseDto> results = answers.stream()
                .map(answer -> new SearchAnswerResponseDto(answer.getId(), answer.getAnswerContent(),
                        answer.getSurvey().getId(), answer.getQuestion().getId(),
                        answer.getUserId()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(results);
    }



}
