package mx.utch.huertos.parcel.service;

import mx.utch.huertos.parcel.api.dto.ParcelRequest;
import mx.utch.huertos.parcel.domain.Parcel;
import mx.utch.huertos.parcel.error.ResourceNotFoundException;
import mx.utch.huertos.parcel.repository.ParcelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ParcelService {
    private final ParcelRepository repository;

    public ParcelService(ParcelRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Parcel create(ParcelRequest request) {
        return repository.save(toParcel(request));
    }

    @Transactional(readOnly = true)
    public List<Parcel> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Parcel findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel " + id + " was not found."));
    }

    @Transactional
    public Parcel update(UUID id, ParcelRequest request) {
        Parcel parcel = findById(id);
        Parcel updated = toParcel(request);
        parcel.update(updated.getName(), updated.getLatitude(), updated.getLongitude(),
                updated.getSoilType(), updated.getOwnerOrLead());
        return repository.save(parcel);
    }

    @Transactional
    public void delete(UUID id) {
        Parcel parcel = findById(id);
        repository.delete(parcel);
    }

    private Parcel toParcel(ParcelRequest request) {
        String name = InputSanitizer.cleanText(request.name(), "name");
        String ownerOrLead = InputSanitizer.cleanText(request.ownerOrLead(), "ownerOrLead");
        return new Parcel(name, request.location().latitude(), request.location().longitude(),
                request.soilType(), ownerOrLead);
    }
}
