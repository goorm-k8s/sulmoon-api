package io.sulmoon.surveyservice.ui;

import io.sulmoon.surveyservice.application.ExampleService;
import io.sulmoon.surveyservice.domain.Example;
import io.sulmoon.surveyservice.dto.ExampleDto;
import io.sulmoon.surveyservice.dto.request.CreateExampleRequestDto;
import io.sulmoon.surveyservice.dto.request.UpdateExampleRequestDto;
import io.sulmoon.surveyservice.dto.response.example.CreateExampleResponseDto;
import io.sulmoon.surveyservice.dto.response.example.DeleteExampleResponseDto;
import io.sulmoon.surveyservice.dto.response.example.SearchExampleResponseDto;
import io.sulmoon.surveyservice.dto.response.example.UpdateExampleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/examples/{exampleId}")
    public ResponseEntity<SearchExampleResponseDto> searchExample(
            @PathVariable Long exampleId) {
        Example example = this.exampleService.searchExample(exampleId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SearchExampleResponseDto(example.getId(), example.getExampleContent(),
                        example.getQuestion().getId()));
    }

    @DeleteMapping("/examples/{exampleId}")
    public ResponseEntity<DeleteExampleResponseDto> deleteExample(
            @PathVariable Long exampleId) {
        Long deletedExampleId = this.exampleService.deleteExample(exampleId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DeleteExampleResponseDto(deletedExampleId));
    }

    @PutMapping("/examples/{exampleId}")
    public ResponseEntity<UpdateExampleResponseDto> updateExample(
            @PathVariable Long exampleId,
            @RequestBody UpdateExampleRequestDto requestData) {
        ExampleDto exampleDto = ExampleDto.builder()
                .id(exampleId)
                .exampleContent(requestData.getExampleContent())
                .build();
        Example example = this.exampleService.updateExample(exampleDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UpdateExampleResponseDto(example.getId(), example.getExampleContent(),
                        example.getQuestion().getId()));
    }

    @PostMapping("/questions/{questionId}/examples")
    public ResponseEntity<CreateExampleResponseDto> createExample(
            @PathVariable Long questionId,
            @RequestBody CreateExampleRequestDto requestData) {
        ExampleDto exampleDto = ExampleDto.builder()
                .exampleContent(requestData.getExampleContent())
                .build();
        Example example = this.exampleService.createExample(questionId, exampleDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateExampleResponseDto(example.getId(), example.getExampleContent(),
                        example.getQuestion().getId()));

    }

    @GetMapping("/questions/{questionId}/examples")
    public ResponseEntity<List<SearchExampleResponseDto>> searchExampleList(
            @PathVariable Long questionId) {
        List<Example> examples = this.exampleService.searchExampleListByQuestion(questionId);
        List<SearchExampleResponseDto> results = examples.stream()
                .map(example -> new SearchExampleResponseDto(example.getId(),
                        example.getExampleContent(), example.getQuestion().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(results);
    }
}
