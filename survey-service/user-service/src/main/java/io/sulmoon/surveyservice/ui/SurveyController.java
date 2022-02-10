package io.sulmoon.surveyservice.ui;

import io.sulmoon.surveyservice.application.AnswerService;
import io.sulmoon.surveyservice.application.ExampleService;
import io.sulmoon.surveyservice.application.QuestionService;
import io.sulmoon.surveyservice.application.SurveyService;
import io.sulmoon.surveyservice.domain.Answer;
import io.sulmoon.surveyservice.domain.Example;
import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.domain.Survey;
import io.sulmoon.surveyservice.dto.ExampleDto;
import io.sulmoon.surveyservice.dto.QuestionDto;
import io.sulmoon.surveyservice.dto.SurveyDto;
import io.sulmoon.surveyservice.dto.request.survey.UpdateSurveyRequestDto;
import io.sulmoon.surveyservice.dto.response.*;
import io.sulmoon.surveyservice.dto.response.survey.CreateSurveyResponseDto;
import io.sulmoon.surveyservice.dto.response.survey.DeleteSurveyResponseDto;
import io.sulmoon.surveyservice.dto.response.survey.SearchSurveyResponseDto;
import io.sulmoon.surveyservice.dto.response.survey.UpdateSurveyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ExampleService exampleService;

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
                .userId(surveyRequestDto.getUserId())
                .build();

        Survey survey = this.surveyService.updateSurvey(surveyDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UpdateSurveyResponseDto(survey.getId(), survey.getTitle(),
                        survey.getDescription(), survey.getUserId()));
    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<SurveyExamplesInfoResponseDto> createSurvey(
            @PathVariable Long userId) {
        Survey survey = this.surveyService.createSurvey(userId);
        Question question = this.questionService.createQuestion(survey.getId(),
                QuestionDto.builder()
                        .userid(userId)
                        .questionContent("Question 1")
                        .multipleSelectionYn(false)
                        .subjectiveYn(false)
                        .build());
        Example example = this.exampleService.createExample(question.getId(),
                ExampleDto.builder()
                        .exampleContent("Option1")
                        .build());

        SurveyExamplesInfoResponseDto result = new SurveyExamplesInfoResponseDto(survey.getId(),
                survey.getTitle(), survey.getDescription());
        QuestionExamplesInfoResponseDto questionDto = new QuestionExamplesInfoResponseDto(
                question.getId(), question.getQuestionContent(), question.getSubjectiveYn(),
                question.getMultipleSelectionYn()
        );
        ExamplesInfoResponseDto exampleDto = new ExamplesInfoResponseDto(
                example.getId(), example.getExampleContent()
        );
        List<ExamplesInfoResponseDto> exampleDtos = List.of(exampleDto);
        questionDto.setExamples(exampleDtos);
        List<QuestionExamplesInfoResponseDto> questionDtos = List.of(questionDto);
        result.setQuestions(questionDtos);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
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

    // 설문의 모든 질문과 질문들의 모든 답변
    @GetMapping("/{surveyId}/answers")
    public ResponseEntity<SurveyAnswersInfoResponseDto> searchQuestionListAndAnswerListBySurvey(
            @PathVariable Long surveyId) {
        // 설문 정보 가져오기
        Survey survey = this.surveyService.searchSurvey(surveyId);
        // 응답 정보 초기화
        SurveyAnswersInfoResponseDto result = new SurveyAnswersInfoResponseDto(survey.getId(), survey.getTitle(), survey.getDescription());
        // 설문에 대한 질문 정보 가져오기
        List<Question> questionList = this.questionService.searchQuestionListBySurvey(surveyId);
        // 응답 정보에 담길 질문 목록 초기화
        List<QuestionAnswersInfoResponseDto> questionsAnswers = new ArrayList<>();

        for (Question question : questionList) {
            // 응답 정보에 담길 질문 초기화
            QuestionAnswersInfoResponseDto questionDto = new QuestionAnswersInfoResponseDto(
                    question.getId(), question.getQuestionContent(),
                    question.getSubjectiveYn(), question.getMultipleSelectionYn());
            // 질문에 대한 답변 목록 가져오기
            List<Answer> answerList = this.answerService.searchAnswerListByQuestion(question.getId());
            // 답변 카운트를 위한 맵 초기화
            Map<String, AnswersInfoResponseDto> answersMap = new HashMap<>();
            for (Answer answer : answerList) {

                if (answersMap.containsKey(answer.getAnswerContent())) {
                    answersMap.get(answer.getAnswerContent()).plusCount();
                    continue;
                }
                answersMap.put(answer.getAnswerContent(), new AnswersInfoResponseDto(
                        answer.getId(), answer.getAnswerContent()
                ));
            }
            // 카운트 정보가 담긴 맵 -> Dto로 변환
            List<AnswersInfoResponseDto> answers = new ArrayList<>(answersMap.values());
            // 응답 정보에 담길 질문에 답변목록 담음
            questionDto.setAnswers(answers);
            // 질문목록에 질문 담음
            questionsAnswers.add(questionDto);
        }
        // 설문 응답에 질문목록 담음
        result.setQuestions(questionsAnswers);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{surveyId}/examples")
    public ResponseEntity<SurveyExamplesInfoResponseDto> searchQuestionListAndExampleListBySurvey(
            @PathVariable Long surveyId) {
        // 설문 정보 가져오기
        Survey survey = this.surveyService.searchSurvey(surveyId);
        // 응답 정보 초기화
        SurveyExamplesInfoResponseDto result = new SurveyExamplesInfoResponseDto(survey.getId(), survey.getTitle(), survey.getDescription());
        // 설문에 대한 질문 정보 가져오기
        List<Question> questionList = this.questionService.searchQuestionListBySurvey(surveyId);
        // 응답 정보에 담길 질문 목록 초기화
        List<QuestionExamplesInfoResponseDto> questionExamples = new ArrayList<>();

        for (Question question : questionList) {
            // 응답 정보에 담길 질문 초기화
            QuestionExamplesInfoResponseDto questionDto = new QuestionExamplesInfoResponseDto(
                    question.getId(), question.getQuestionContent(),
                    question.getSubjectiveYn(), question.getMultipleSelectionYn());
            // 질문에 대한 보기 목록 가져오기
            List<Example> exampleList = this.exampleService.searchExampleListByQuestion(question.getId());
            List<ExamplesInfoResponseDto> examples = new ArrayList<>();
            for (Example example : exampleList) {
                examples.add(new ExamplesInfoResponseDto(example.getId(), example.getExampleContent()));
            }
            // 응답 정보에 담길 질문에 보기목록 담음
            questionDto.setExamples(examples);
            // 질문목록에 질문 담음
            questionExamples.add(questionDto);
        }
        // 설문 응답에 질문목록 담음
        result.setQuestions(questionExamples);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
