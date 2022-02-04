package io.sulmoon.surveyservice.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SearchAnswerResponseDto {

    private final Long id;
    private final String answerContent;
    private final Long surveyId;
    private final Long questionId;
    private final Long userId;
}
