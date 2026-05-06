package com.freightflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entity representing a Shipment (Рейс/Отгрузка).
 */
@Entity
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Груз должен быть указан")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @NotNull(message = "Перевозчик должен быть указан")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier;

    @NotBlank(message = "Пункт отправления должен быть указан")
    @Column(nullable = false)
    private String origin;

    @NotBlank(message = "Пункт назначения должен быть указан")
    @Column(nullable = false)
    private String destination;

    @NotNull(message = "Дата и время отправки должны быть указаны")
    @Column(name = "scheduled_departure", nullable = false)
    private LocalDateTime scheduledDeparture;

    @NotNull(message = "Статус рейса должен быть указан")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum ShipmentStatus {
        CREATED,
        IN_PROGRESS,
        DELIVERED,
        CANCELLED
    }

    // Constructors
    public Shipment() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Shipment(Cargo cargo, Carrier carrier, String origin, String destination,
                    LocalDateTime scheduledDeparture, ShipmentStatus status) {
        this();
        this.cargo = cargo;
        this.carrier = carrier;
        this.origin = origin;
        this.destination = destination;
        this.scheduledDeparture = scheduledDeparture;
        this.status = status;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getScheduledDeparture() {
        return scheduledDeparture;
    }

    public void setScheduledDeparture(LocalDateTime scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
