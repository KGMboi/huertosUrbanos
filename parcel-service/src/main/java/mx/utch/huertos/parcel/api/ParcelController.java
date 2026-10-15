package mx.utch.huertos.parcel.api;

import jakarta.validation.Valid;
import mx.utch.huertos.parcel.api.dto.ParcelRequest;
import mx.utch.huertos.parcel.api.dto.ParcelResponse;
import mx.utch.huertos.parcel.service.ParcelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parcels")
public class ParcelController {
    private final ParcelService service;

    public ParcelController(ParcelService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParcelResponse create(@Valid @RequestBody ParcelRequest request) {
        return ParcelResponse.from(service.create(request));
    }

    @GetMapping
    public List<ParcelResponse> findAll() {
        return service.findAll().stream().map(ParcelResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ParcelResponse findById(@PathVariable("id") UUID id) {
        return ParcelResponse.from(service.findById(id));
    }

    @PutMapping("/{id}")
    public ParcelResponse update(@PathVariable("id") UUID id, @Valid @RequestBody ParcelRequest request) {
        return ParcelResponse.from(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        service.delete(id);
    }
}
