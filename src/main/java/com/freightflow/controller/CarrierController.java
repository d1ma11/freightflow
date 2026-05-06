package com.freightflow.controller;

import com.freightflow.model.Carrier;
import com.freightflow.model.Carrier.TransportType;
import com.freightflow.service.CarrierService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Carrier operations.
 */
@RestController
@RequestMapping("/api/carriers")
public class CarrierController {

    private final CarrierService carrierService;

    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    /**
     * Get all carriers, optionally filtered by transport type.
     */
    @GetMapping
    public ResponseEntity<List<Carrier>> getAllCarriers(
            @RequestParam(required = false) TransportType transportType) {
        List<Carrier> carriers;
        if (transportType != null) {
            carriers = carrierService.getCarriersByTransportType(transportType);
        } else {
            carriers = carrierService.getAllCarriers();
        }
        return ResponseEntity.ok(carriers);
    }

    /**
     * Get carrier by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Carrier> getCarrierById(@PathVariable Long id) {
        Carrier carrier = carrierService.getCarrierById(id);
        return ResponseEntity.ok(carrier);
    }

    /**
     * Create a new carrier.
     */
    @PostMapping
    public ResponseEntity<Carrier> createCarrier(@Valid @RequestBody Carrier carrier) {
        Carrier createdCarrier = carrierService.createCarrier(carrier);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCarrier);
    }

    /**
     * Update an existing carrier.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Carrier> updateCarrier(@PathVariable Long id,
                                                 @Valid @RequestBody Carrier carrier) {
        Carrier updatedCarrier = carrierService.updateCarrier(id, carrier);
        return ResponseEntity.ok(updatedCarrier);
    }

    /**
     * Delete a carrier (if not used in active shipments).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrier(@PathVariable Long id) {
        carrierService.deleteCarrier(id);
        return ResponseEntity.noContent().build();
    }
}
