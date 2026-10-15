package mx.utch.huertos.sensor.repository;

import mx.utch.huertos.sensor.domain.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SensorReadingRepository extends JpaRepository<SensorReading, UUID> {
    List<SensorReading> findAllByOrderByRecordedAtDesc();
    List<SensorReading> findByParcelIdOrderByRecordedAtDesc(UUID parcelId);
}
