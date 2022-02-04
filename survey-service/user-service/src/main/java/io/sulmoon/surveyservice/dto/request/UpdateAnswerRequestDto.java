package io.sulmoon.surveyservice.dto.request;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAnswerRequestDto {
    private String answerContent;
}
