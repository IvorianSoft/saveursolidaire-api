package com.ivoriandev.saveursolidaire.utils.dto.store;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchStoreDto {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    private Integer radius;
}
