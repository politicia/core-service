package com.politicia.coreservice.domain.target;

import com.politicia.coreservice.domain.Target;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Series extends Target {
    @Enumerated(EnumType.STRING)
    private SeriesType seriesType;

}
