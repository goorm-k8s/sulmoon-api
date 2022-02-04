package io.sulmoon.surveyservice.ui;

import io.sulmoon.surveyservice.application.SurveyService;
import io.sulmoon.surveyservice.domain.Survey;
import io.sulmoon.surveyservice.dto.SurveyDto;
import io.sulmoon.surveyservice.dto.request.UpdateSurveyRequestDto;
import io.sulmoon.surveyservice.dto.response.CreateSurveyResponseDto;
import io.sulmoon.surveyservice.dto.response.DeleteSurveyResponseDto;
import io.sulmoon.surveyservice.dto.response.SearchSurveyResponseDto;
import io.sulmoon.surveyservice.dto.response.UpdateSurveyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("/test")
    public String test() {
        return "hello world";
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<SearchSurveyResponseDto> searchSurvey(@PathVariable Long surveyId) {
        Survey survey = this.surveyService.searchSurvey(surveyId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SearchSurveyResponseDto(survey.getId(), survey.getTitle(),
                        survey.getDescription(), survey.getUserId()));
    }

    @DeleteMapping("/{surveyId}")
    public ResponseEntity<DeleteSurveyResponseDto> deleteSurvey(@PathVariable Long surveyId) {
        Long deletedSurveyId = this.surveyService.deleteSurvey(surveyId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DeleteSurveyResponseDto(deletedSurveyId));
    }

    @PutMapping("/{surveyId}")
    public ResponseEntity<UpdateSurveyResponseDto> updateSurvey(
            @RequestBody UpdateSurveyRequestDto surveyRequestDto,
            @PathVariable Long surveyId) {
        SurveyDto surveyDto = SurveyDto.builder()
                .id(surveyId)
                .title(surveyRequestDto.getTitle())
                .description(surveyRequestDto.getDescription())
                .build();

        Survey survey = this.surveyService.updateSurvey(surveyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UpdateSurveyResponseDto(survey.getId(), survey.getTitle(),
                        survey.getDescription(), survey.getUserId()));
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<CreateSurveyResponseDto> createSurvey(
            @PathVariable Long userId) {
        Survey survey = this.surveyService.createSurvey(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateSurveyResponseDto(survey.getId(), survey.getTitle(),
                        survey.getDescription(), survey.getUserId()));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<SearchSurveyResponseDto>> searchSurveyList(
            @PathVariable Long userId) {
        List<Survey> surveys = this.surveyService.searchSurveyListByUserId(userId);
        List<SearchSurveyResponseDto> results = surveys.stream()
                .map(survey -> new SearchSurveyResponseDto(survey.getId(), survey.getTitle(),
                        survey.getDescription(), survey.getUserId()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(results);
    }

    @GetMapping("/users/{userId}/answers")
    public ResponseEntity<List<SearchSurveyResponseDto>> searchMyAnswerSurveyList(
            @PathVariable Long userId) {
        List<Survey> surveys = this.surveyService.searchMyAnswerSurveyListByUserId(userId);
        List<SearchSurveyResponseDto> results = surveys.stream()
                .map(survey -> new SearchSurveyResponseDto(survey.getId(), survey.getTitle(),
                        survey.getDescription(), survey.getUserId()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(results);
    }
}
