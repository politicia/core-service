package com.politicia.coreservice.domain;

import com.politicia.coreservice.domain.target.Series;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Record extends EntityPrefix {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;

}
