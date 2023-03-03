package com.politicia.coreservice.domain.target;

import com.politicia.coreservice.domain.Target;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Series extends Target {
    @Enumerated(EnumType.STRING)
    private SeriesType seriesType;

    @Builder
    public Series(Long id, String name, String icon, SeriesType seriesType) {
        setId(id);
        setName(name);
        setIcon(icon);
        this.seriesType = seriesType;
    }
}
