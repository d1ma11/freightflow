package com.freightflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Entity representing a Carrier (Перевозчик).
 */
@Entity
@Table(name = "carrier")
public class Carrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название компании не может быть пустым")
    @Column(nullable = false)
    private String name;

    @Column(name = "contact_person")
    private String contactPerson;

    private String phone;

    @NotNull(message = "Тип транспорта должен быть указан")
    @Enumerated(EnumType.STRING)
    @Column(name = "transport_type", nullable = false)
    private TransportType transportType;

    @NotNull(message = "Грузоподъемность должна быть указана")
    @Positive(message = "Грузоподъемность должна быть положительной")
    @Column(name = "capacity_kg", nullable = false)
    private Double capacityKg;

    public enum TransportType {
        TRUCK,
        VAN,
        REFRIGERATOR,
        CONTAINER
    }

    // Constructors
    public Carrier() {
    }

    public Carrier(String name, String contactPerson, String phone,
                   TransportType transportType, Double capacityKg) {
        this.name = name;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.transportType = transportType;
        this.capacityKg = capacityKg;
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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public Double getCapacityKg() {
        return capacityKg;
    }

    public void setCapacityKg(Double capacityKg) {
        this.capacityKg = capacityKg;
    }
}
