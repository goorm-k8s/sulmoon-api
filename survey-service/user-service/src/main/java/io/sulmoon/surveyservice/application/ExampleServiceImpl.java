package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Example;
import io.sulmoon.surveyservice.domain.Question;
import io.sulmoon.surveyservice.domain.repository.ExampleRepository;
import io.sulmoon.surveyservice.domain.repository.QuestionRepository;
import io.sulmoon.surveyservice.dto.ExampleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService{

    private final ExampleRepository exampleRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public Example createExample(Long questionId, ExampleDto exampleDto) {
        Question question = questionRepository.findById(questionId).orElseThrow(NoSuchElementException::new);
        Example example = Example.builder()
                .exampleContent(exampleDto.getExampleContent())
                .question(question)
                .build();
        return exampleRepository.save(example);
    }

    @Override
    public Example searchExample(Long exampleId) {
        return exampleRepository.findById(exampleId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    public Long deleteExample(Long exampleId) {
        return exampleRepository.deleteExampleById(exampleId);
    }

    @Override
    @Transactional
    public Example updateExample(ExampleDto exampleDto) {
        Example example = exampleRepository.findById(exampleDto.getId())
                .orElse(createExample(exampleDto.getQuestion().getId(), exampleDto));
        example.setExampleContent(exampleDto.getExampleContent());
        return example;
    }

    @Override
    public List<Example> searchExampleListByQuestion(Long questionId) {
        Question question = questionRepository.getById(questionId);
        return exampleRepository.findAllByQuestion(question);
    }
}
