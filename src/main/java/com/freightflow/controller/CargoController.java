package com.freightflow.controller;

import com.freightflow.model.Cargo;
import com.freightflow.model.Cargo.CargoType;
import com.freightflow.service.CargoService;
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
 * REST Controller for Cargo operations.
 */
@RestController
@RequestMapping("/api/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    /**
     * Get all cargos, optionally filtered by type.
     */
    @GetMapping
    public ResponseEntity<List<Cargo>> getAllCargos(
            @RequestParam(required = false) CargoType type) {
        List<Cargo> cargos;
        if (type != null) {
            cargos = cargoService.getCargosByType(type);
        } else {
            cargos = cargoService.getAllCargos();
        }
        return ResponseEntity.ok(cargos);
    }

    /**
     * Get cargo by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cargo> getCargoById(@PathVariable Long id) {
        Cargo cargo = cargoService.getCargoById(id);
        return ResponseEntity.ok(cargo);
    }

    /**
     * Create a new cargo.
     */
    @PostMapping
    public ResponseEntity<Cargo> createCargo(@Valid @RequestBody Cargo cargo) {
        Cargo createdCargo = cargoService.createCargo(cargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCargo);
    }

    /**
     * Update an existing cargo.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cargo> updateCargo(@PathVariable Long id,
                                             @Valid @RequestBody Cargo cargo) {
        Cargo updatedCargo = cargoService.updateCargo(id, cargo);
        return ResponseEntity.ok(updatedCargo);
    }

    /**
     * Delete a cargo (if not used in active shipments).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        cargoService.deleteCargo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get count of cargos by type.
     */
    @GetMapping("/count-by-type")
    public ResponseEntity<Integer> countCargosByType(
            @RequestParam CargoType type) {
        int count = cargoService.countCargosByType(type);
        return ResponseEntity.ok(count);
    }
}
