package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Survey;
import io.sulmoon.surveyservice.domain.repository.SurveyRepository;
import io.sulmoon.surveyservice.dto.SurveyDto;
import io.sulmoon.surveyservice.dto.request.UpdateSurveyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    @Override
    @Transactional
    public Survey createSurvey(Long userId) {
        Survey survey = Survey.builder()
                .title("제목을 입력해주세요.")
                .description("설명")
                .userId(userId)
                .build();
        survey.setCreator(userId+"");
        survey.setModifier(userId+"");
        survey.setCreated(LocalDateTime.now());
        survey.setModified(LocalDateTime.now());
        return surveyRepository.save(survey);
    }

    @Override
    public Survey searchSurvey(Long surveyId) {
        return surveyRepository.getById(surveyId);
    }

    @Override
    @Transactional
    public Long deleteSurvey(Long surveyId) {
        return surveyRepository.deleteSurveyById(surveyId);
    }

    @Override
    @Transactional
    public Survey updateSurvey(SurveyDto surveyDto) {
        Survey survey = surveyRepository.getById(surveyDto.getId());
        survey.setTitle(surveyDto.getTitle());
        survey.setDescription(surveyDto.getDescription());
        survey.setModifier(surveyDto.getUserId()+"");
        survey.setModified(LocalDateTime.now());
        return survey;
    }

    @Override
    public List<Survey> searchSurveyListByUserId(Long userId) {
        return surveyRepository.findAllByUserId(userId);
    }

    @Override
    public List<Survey> searchMyAnswerSurveyListByUserId(Long userId) {
        return surveyRepository.findAllAnswersByUserId(userId);
    }


}
