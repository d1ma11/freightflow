package com.freightflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Entity representing a Cargo (Груз).
 */
@Entity
@Table(name = "cargo")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название груза не может быть пустым")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Вес груза должен быть указан")
    @PositiveOrZero(message = "Вес груза не может быть отрицательным")
    @Column(name = "weight_kg", nullable = false)
    private Double weightKg;

    @NotNull(message = "Объем груза должен быть указан")
    @PositiveOrZero(message = "Объем груза не может быть отрицательным")
    @Column(name = "volume_cubic_meters", nullable = false)
    private Double volumeCubicMeters;

    @NotNull(message = "Тип груза должен быть указан")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoType type;

    public enum CargoType {
        GENERAL,
        FRAGILE,
        REFRIGERATED,
        DANGEROUS
    }

    // Constructors
    public Cargo() {
    }

    public Cargo(String name, Double weightKg, Double volumeCubicMeters, CargoType type) {
        this.name = name;
        this.weightKg = weightKg;
        this.volumeCubicMeters = volumeCubicMeters;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public Double getVolumeCubicMeters() {
        return volumeCubicMeters;
    }

    public void setVolumeCubicMeters(Double volumeCubicMeters) {
        this.volumeCubicMeters = volumeCubicMeters;
    }

    public CargoType getType() {
        return type;
    }

    public void setType(CargoType type) {
        this.type = type;
    }
}
