package io.sulmoon.surveyservice.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
public class AnswerInfoRequestDto {
    private final Long answerId;
    private final Long questionId;
    private final Long userId;
    private final String answerContent;

}
