package mx.utch.huertos.sensor.api.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import mx.utch.huertos.sensor.domain.MeasurementType;

import java.math.BigDecimal;
import java.util.UUID;

public record SensorReadingRequest(
        @NotNull UUID parcelId,
        @NotNull MeasurementType type,
        @NotNull @Digits(integer = 12, fraction = 6) BigDecimal value,
        @NotBlank @Size(max = 20) String unit) {
}
