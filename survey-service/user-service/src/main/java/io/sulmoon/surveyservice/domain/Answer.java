package io.sulmoon.surveyservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "ANSWER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Answer extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWER_ID")
    private Long id;

    @Column(name = "ANSWER_CONTENT")
    private String answerContent;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SURVEY_ID")
    @JsonIgnore
    private Survey survey;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "QUESTION_ID")
    @JsonIgnore
    private Question question;

    @Column(name = "USER_ID")
    private Long userId;

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(getId(), answer.getId()) && Objects.equals(getAnswerContent(), answer.getAnswerContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAnswerContent());
    }

}
