package io.sulmoon.surveyservice.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "SURVEY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Survey extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SURVEY_ID")
    private Long id;

    // private User user;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "USER_ID")
    private Long userId;

    @OneToMany(mappedBy = "survey", fetch = LAZY)
    private List<Answer> answerList = new ArrayList<>();


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return Objects.equals(id, survey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
