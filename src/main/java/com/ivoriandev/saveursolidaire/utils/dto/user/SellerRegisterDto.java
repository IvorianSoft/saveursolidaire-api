package com.ivoriandev.saveursolidaire.utils.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SellerRegisterDto extends UserRegisterDto {
    @NotNull
    private String storeName;
    @NotNull
    private String storeDescription;
    @NotNull
    private String storeContact;
    @NotNull
    private String storeAddress;
    @NotNull
    private String storeCity;
    @NotNull
    private String storeCountry;
    @NotNull
    private String storePostalCode;
    private String storeComplement;
    @NotNull
    private Double storeLatitude;
    @NotNull
    private Double storeLongitude;
}
