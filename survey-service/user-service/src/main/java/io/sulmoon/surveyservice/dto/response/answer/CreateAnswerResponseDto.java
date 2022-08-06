package io.sulmoon.surveyservice.dto.response.answer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateAnswerResponseDto {

    private final Long id;
    private final String answerContent;
    private final Long surveyId;
    private final Long questionId;
    private final Long userId;
}
