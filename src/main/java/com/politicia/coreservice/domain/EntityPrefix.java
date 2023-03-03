package com.politicia.coreservice.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class EntityPrefix {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
