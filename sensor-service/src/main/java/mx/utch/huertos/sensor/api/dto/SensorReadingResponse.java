package mx.utch.huertos.sensor.api.dto;

import mx.utch.huertos.sensor.domain.MeasurementType;
import mx.utch.huertos.sensor.domain.SensorReading;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record SensorReadingResponse(
        UUID id,
        UUID parcelId,
        MeasurementType type,
        BigDecimal value,
        String unit,
        Instant recordedAt) {
    public static SensorReadingResponse from(SensorReading reading) {
        return new SensorReadingResponse(reading.getId(), reading.getParcelId(), reading.getType(),
                reading.getValue(), reading.getUnit(), reading.getRecordedAt());
    }
}
