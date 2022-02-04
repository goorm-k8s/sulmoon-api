package io.sulmoon.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

public enum UserRole {
    ROLE_USER,  // 사용자 권한
    ROLE_ADMIN  // 관리자 권한
}
