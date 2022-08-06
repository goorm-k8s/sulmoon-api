package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Example;
import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.dto.ExampleDto;
import io.sulmoon.surveyservice.dto.QuestionDto;

import java.util.List;

public interface ExampleService {
    Example createExample(Long questionId, ExampleDto exampleDto);

    Example searchExample(Long exampleId);

    Long deleteExample(Long exampleId);

    Example updateExample(ExampleDto exampleDto);

    List<Example> searchExampleListByQuestion(Long questionId);
}
