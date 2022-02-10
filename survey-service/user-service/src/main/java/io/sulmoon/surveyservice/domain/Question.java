package io.sulmoon.surveyservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "QUESTION")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private Long id;

//    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
//    private List<Example> examples = new ArrayList<>();

    @Column(name = "QUESTION_CONTENT")
    private String questionContent;

    @Column(name = "SUBJECTIVE_YN")
    private Boolean subjectiveYn;

    @Column(name = "MULTIPLE_SELECTION_YN")
    private Boolean multipleSelectionYn;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SURVEY_ID")
    @JsonIgnore
    private Survey survey;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Example> exampleList = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answerList = new ArrayList<>();


    //==연관관계 메서드==//
//    public void addExample(Example example) {
//        examples.add(example);
//        example.setQuestion(this);
//    }

    //==생성 메서드==//
//    public static Question createQuestion(Example... examples) {
//        Question question = new Question();
//        for (Example example : examples) {
//            question.addExample(example);
//        }
//        return question;
//    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public void setSubjectiveYn(Boolean subjectiveYn) {
        this.subjectiveYn = subjectiveYn;
    }

    public void setMultipleSelectionYn(Boolean multipleSelectionYn) {
        this.multipleSelectionYn = multipleSelectionYn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
