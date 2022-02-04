package io.sulmoon.userservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity {

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "MODIFIER")
    private String modifier;

    @Column(name = "CREATED")
    private LocalDateTime created;

    @Column(name = "MODIFIED")
    private LocalDateTime modified;

}
