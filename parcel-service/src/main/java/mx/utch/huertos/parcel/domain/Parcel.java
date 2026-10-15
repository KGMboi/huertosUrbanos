package mx.utch.huertos.parcel.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "parcels")
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SoilType soilType;

    @Column(nullable = false, length = 100)
    private String ownerOrLead;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Parcel() {
    }

    public Parcel(String name, double latitude, double longitude, SoilType soilType, String ownerOrLead) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.soilType = soilType;
        this.ownerOrLead = ownerOrLead;
    }

    @PrePersist
    void setCreatedAt() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public SoilType getSoilType() { return soilType; }
    public String getOwnerOrLead() { return ownerOrLead; }
    public Instant getCreatedAt() { return createdAt; }

    public void update(String name, double latitude, double longitude, SoilType soilType, String ownerOrLead) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.soilType = soilType;
        this.ownerOrLead = ownerOrLead;
    }
}
