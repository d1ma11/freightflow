package com.freightflow.controller;

import com.freightflow.model.Shipment;
import com.freightflow.model.Shipment.ShipmentStatus;
import com.freightflow.service.ShipmentService;
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
 * REST Controller for Shipment operations.
 */
@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    /**
     * Get all shipments, optionally filtered by status or carrier ID.
     */
    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments(
            @RequestParam(required = false) ShipmentStatus status,
            @RequestParam(required = false) Long carrierId) {
        List<Shipment> shipments;
        if (status != null) {
            shipments = shipmentService.getShipmentsByStatus(status);
        } else if (carrierId != null) {
            shipments = shipmentService.getShipmentsByCarrierId(carrierId);
        } else {
            shipments = shipmentService.getAllShipments();
        }
        return ResponseEntity.ok(shipments);
    }

    /**
     * Get shipment by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id) {
        Shipment shipment = shipmentService.getShipmentById(id);
        return ResponseEntity.ok(shipment);
    }

    /**
     * Create a new shipment.
     * Request body should contain cargo.id and carrier.id.
     */
    @PostMapping
    public ResponseEntity<Shipment> createShipment(
            @Valid @RequestBody Shipment shipment) {
        Shipment createdShipment = shipmentService.createShipment(shipment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdShipment);
    }

    /**
     * Update an existing shipment (status, scheduled departure, etc.).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Shipment> updateShipment(
            @PathVariable Long id,
            @Valid @RequestBody Shipment shipment) {
        Shipment updatedShipment = shipmentService.updateShipment(id, shipment);
        return ResponseEntity.ok(updatedShipment);
    }

    /**
     * Cancel a shipment (sets status to CANCELLED).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Shipment> cancelShipment(@PathVariable Long id) {
        Shipment cancelledShipment = shipmentService.cancelShipment(id);
        return ResponseEntity.ok(cancelledShipment);
    }
}
