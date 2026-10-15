package mx.utch.huertos.sensor.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sensor_readings")
public class SensorReading {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID parcelId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MeasurementType type;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal value;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(nullable = false, updatable = false)
    private Instant recordedAt;

    protected SensorReading() {
    }

    public SensorReading(UUID parcelId, MeasurementType type, BigDecimal value, String unit) {
        this.parcelId = parcelId;
        this.type = type;
        this.value = value;
        this.unit = unit;
    }

    @PrePersist
    void setRecordedAt() {
        if (recordedAt == null) {
            recordedAt = Instant.now();
        }
    }

    public UUID getId() { return id; }
    public UUID getParcelId() { return parcelId; }
    public MeasurementType getType() { return type; }
    public BigDecimal getValue() { return value; }
    public String getUnit() { return unit; }
    public Instant getRecordedAt() { return recordedAt; }
}
