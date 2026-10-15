package mx.utch.huertos.sensor.service;

import mx.utch.huertos.sensor.api.dto.SensorReadingRequest;
import mx.utch.huertos.sensor.domain.SensorReading;
import mx.utch.huertos.sensor.error.ResourceNotFoundException;
import mx.utch.huertos.sensor.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SensorReadingService {
    private final SensorReadingRepository repository;
    private final ParcelClient parcelClient;

    public SensorReadingService(SensorReadingRepository repository, ParcelClient parcelClient) {
        this.repository = repository;
        this.parcelClient = parcelClient;
    }

    @Transactional
    public SensorReading create(SensorReadingRequest request) {
        parcelClient.requireExists(request.parcelId());
        String unit = InputSanitizer.cleanText(request.unit(), "unit");
        return repository.save(new SensorReading(request.parcelId(), request.type(), request.value(), unit));
    }

    @Transactional(readOnly = true)
    public List<SensorReading> findAll(UUID parcelId) {
        return parcelId == null ? repository.findAllByOrderByRecordedAtDesc()
                : repository.findByParcelIdOrderByRecordedAtDesc(parcelId);
    }

    @Transactional(readOnly = true)
    public SensorReading findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor reading " + id + " was not found."));
    }
}
