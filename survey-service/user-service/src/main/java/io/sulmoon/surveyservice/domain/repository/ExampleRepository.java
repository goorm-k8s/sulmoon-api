package io.sulmoon.surveyservice.domain.repository;

import io.sulmoon.surveyservice.domain.Example;
import io.sulmoon.surveyservice.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExampleRepository extends JpaRepository<Example, Long> {

    Long deleteExampleById(Long exampleId);

    List<Example> findAllByQuestion(Question question);
}
