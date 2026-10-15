package mx.utch.huertos.sensor.api;

import jakarta.validation.Valid;
import mx.utch.huertos.sensor.api.dto.SensorReadingRequest;
import mx.utch.huertos.sensor.api.dto.SensorReadingResponse;
import mx.utch.huertos.sensor.service.SensorReadingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/readings")
public class SensorReadingController {
    private final SensorReadingService service;

    public SensorReadingController(SensorReadingService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SensorReadingResponse create(@Valid @RequestBody SensorReadingRequest request) {
        return SensorReadingResponse.from(service.create(request));
    }

    @GetMapping
    public List<SensorReadingResponse> findAll(@RequestParam(name = "parcelId", required = false) UUID parcelId) {
        return service.findAll(parcelId).stream().map(SensorReadingResponse::from).toList();
    }

    @GetMapping("/{id}")
    public SensorReadingResponse findById(@PathVariable("id") UUID id) {
        return SensorReadingResponse.from(service.findById(id));
    }
}
