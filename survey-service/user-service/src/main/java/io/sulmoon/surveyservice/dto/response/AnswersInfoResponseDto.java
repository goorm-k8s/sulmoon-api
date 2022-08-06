package io.sulmoon.surveyservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AnswersInfoResponseDto {
    private final Long answerId;
    private final String answerContent;
    private int count;

    public AnswersInfoResponseDto(Long answerId, String answerContent) {
        this.answerId = answerId;
        this.answerContent = answerContent;
        this.count = 1;
    }

    public void plusCount() {
        this.count++;
    }
}
