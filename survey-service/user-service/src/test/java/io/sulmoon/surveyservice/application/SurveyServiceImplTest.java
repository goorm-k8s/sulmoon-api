package io.sulmoon.surveyservice.application;

import io.sulmoon.surveyservice.domain.Survey;
import io.sulmoon.surveyservice.domain.repository.SurveyRepository;
import io.sulmoon.surveyservice.dto.SurveyDto;
import io.sulmoon.surveyservice.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SurveyServiceImplTest {

    private final SurveyRepository mockSurveyRepository = mock(SurveyRepository.class);
    private final SurveyService surveyService = new SurveyServiceImpl(mockSurveyRepository);

    Long userId;
    Long otherUserId;
    Long surveyId;
    String title;
    String description;
    String creator;
    String modifier;
    LocalDateTime created;
    LocalDateTime modified;
    User user;
    Survey survey1;
    Survey survey2;
    Survey survey3;
    Survey survey4;
    List<Survey> surveys;
    List<Survey> otherUserServeys;

    @BeforeEach
    void setUp() {
        userId = 1L;
        otherUserId = 2L;
        surveyId = 1L;
        title = "제목을 입력해주세요.";
        description = "설명";
        creator = "tempCreator";
        modifier = "tempCreator";
        created = LocalDateTime.now();
        modified = LocalDateTime.now();
        user = User.builder()
                .id(userId)
                .email("example@gmail.com")
                .providerId("abcd1234")
                .build();
        survey1 = Survey.builder()
                .id(1L)
                .title(title)
                .description(description)
                .userId(userId)
                .build();
       survey2 = Survey.builder()
                .id(2L)
                .title(title)
                .description(description)
                .userId(userId)
                .build();
        survey3 = Survey.builder()
                .id(3L)
                .title(title)
                .description(description)
                .userId(otherUserId)
                .build();
        survey4 = Survey.builder()
                .id(4L)
                .title(title)
                .description(description)
                .userId(otherUserId)
                .build();
        surveys = Arrays.asList(survey1, survey2);
        otherUserServeys = Arrays.asList(survey3, survey4);
    }

    @Test
    @DisplayName("설문 생성")
    void 설문_생성() {
        Survey givenSurvey = Survey.builder()
                .title(title)
                .description(description)
                .userId(userId)
                .build();
        given(mockSurveyRepository.save(givenSurvey)).willReturn(survey1);

        Survey result = this.surveyService.createSurvey(userId);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getTitle()).isEqualTo(title),
                () -> assertThat(result.getDescription()).isEqualTo(description),
                () -> assertThat(result.getUserId()).isEqualTo(userId)
        );
    }

    @Test
    @DisplayName("설문 검색")
    void 설문_검색() {
        given(mockSurveyRepository.getById(surveyId)).willReturn(survey1);

        Survey result = this.surveyService.searchSurvey(1L);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getTitle()).isEqualTo(title),
                () -> assertThat(result.getDescription()).isEqualTo(description),
                () -> assertThat(result.getUserId()).isEqualTo(userId)
        );
    }

    @Test
    @DisplayName("작성한 설문 목록")
    void 설문_목록() {

        given(mockSurveyRepository.findAllByUserId(userId)).willReturn(surveys);

        List<Survey> results = this.surveyService.searchSurveyListByUserId(userId);

        assertAll(
                () -> assertThat(results.get(0).getId()).isEqualTo(1L),
                () -> assertThat(results.get(1).getId()).isEqualTo(2L),
                () -> assertThat(results.get(0).getTitle()).isEqualTo(title),
                () -> assertThat(results.get(1).getTitle()).isEqualTo(title),
                () -> assertThat(results.get(0).getUserId()).isEqualTo(userId),
                () -> assertThat(results.get(1).getUserId()).isEqualTo(userId)
        );

    }

    @Test
    @DisplayName("설문 수정")
    void 설문_수정() {

        SurveyDto updatedSurveyDto = SurveyDto.builder()
                .id(surveyId)
                .title("수정된 제목")
                .description("수정된 설명")
                .creator(creator)
                .modifier("수정자")
                .created(created)
                .modified(LocalDateTime.now())
                .build();

        given(mockSurveyRepository.getById(surveyId)).willReturn(survey1);

        Survey result = this.surveyService.updateSurvey(updatedSurveyDto);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getTitle()).isEqualTo(updatedSurveyDto.getTitle()),
                () -> assertThat(result.getDescription()).isEqualTo(updatedSurveyDto.getDescription()),
                () -> assertThat(result.getModifier()).isEqualTo(updatedSurveyDto.getModifier()),
                () -> assertThat(result.getModified()).isEqualTo(updatedSurveyDto.getModified()),
                () -> assertThat(result.getUserId()).isEqualTo(userId)
        );
    }

    @Test
    @DisplayName("설문 삭제")
    void 설문_삭제() {
        given(mockSurveyRepository.deleteSurveyById(surveyId)).willReturn(1L);

        Long result = this.surveyService.deleteSurvey(surveyId);

        assertThat(result).isEqualTo(1L);
    }


    @Test
    @DisplayName("회원이 답변한 설문 목록조회")
    void 답변_설문_조회() {
        given(mockSurveyRepository.findAllAnswersByUserId(userId)).willReturn(otherUserServeys);

        List<Survey> results = this.surveyService.searchMyAnswerSurveyListByUserId(userId);

        assertAll(
                () -> assertThat(results.get(0).getId()).isEqualTo(3L),
                () -> assertThat(results.get(1).getId()).isEqualTo(4L),
                () -> assertThat(results.get(0).getUserId()).isEqualTo(2L),
                () -> assertThat(results.get(1).getUserId()).isEqualTo(2L)
        );
    }

}