package mx.utch.huertos.parcel.api.dto;

import mx.utch.huertos.parcel.domain.Parcel;
import mx.utch.huertos.parcel.domain.SoilType;

import java.time.Instant;
import java.util.UUID;

public record ParcelResponse(
        UUID id,
        String name,
        LocationRequest location,
        SoilType soilType,
        String ownerOrLead,
        Instant createdAt) {
    public static ParcelResponse from(Parcel parcel) {
        return new ParcelResponse(
                parcel.getId(),
                parcel.getName(),
                new LocationRequest(parcel.getLatitude(), parcel.getLongitude()),
                parcel.getSoilType(),
                parcel.getOwnerOrLead(),
                parcel.getCreatedAt());
    }
}
