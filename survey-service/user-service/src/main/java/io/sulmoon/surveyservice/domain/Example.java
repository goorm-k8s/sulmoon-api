package io.sulmoon.surveyservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "EXAMPLE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Example {

    @Column(name = "EXAMPLE_ID")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EXAMPLE_CONTENT")
    private String exampleContent;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SURVEY_ID")
    @JsonIgnore
    private Survey survey;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "QUESTION_ID")
    @JsonIgnore
    private Question question;

//    //==생성 메서드==//
//    public static Example createExample(Question question) {
//        Example example = new Example();
//        example.setQuestion(question);
//        return example;
//    }

    public void setExampleContent(String exampleContent) {
        this.exampleContent = exampleContent;
    }

//    public void setQuestion(Question question) {
//        this.question = question;
//        question.getExamples().add(this);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Example example = (Example) o;
        return Objects.equals(getId(), example.getId()) && Objects.equals(getExampleContent(), example.getExampleContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getExampleContent());
    }

}
