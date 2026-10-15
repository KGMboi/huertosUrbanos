package mx.utch.huertos.parcel.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import mx.utch.huertos.parcel.domain.SoilType;

public record ParcelRequest(
        @NotBlank @Size(max = 100) String name,
        @NotNull @Valid LocationRequest location,
        @NotNull SoilType soilType,
        @NotBlank @Size(max = 100) String ownerOrLead) {
}
