package io.sulmoon.surveyservice.application;


import io.sulmoon.surveyservice.domain.Survey;
import io.sulmoon.surveyservice.dto.SurveyDto;

import java.util.List;

public interface SurveyService {

    Survey createSurvey(Long userId);

    Survey searchSurvey(Long surveyId);

    Long deleteSurvey(Long surveyId);

    Survey updateSurvey(SurveyDto surveyDto);

    List<Survey> searchSurveyListByUserId(Long userId);

    List<Survey> searchMyAnswerSurveyListByUserId(Long userId);
}
