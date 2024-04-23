package com.ivoriandev.saveursolidaire.utils.dto.geospatial;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class SearchDto {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    private Integer radius;
}
