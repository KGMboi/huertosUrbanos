package mx.utch.huertos.parcel.repository;

import mx.utch.huertos.parcel.domain.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParcelRepository extends JpaRepository<Parcel, UUID> {
}
