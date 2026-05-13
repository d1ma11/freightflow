package com.freightflow.service;

import com.freightflow.exception.BusinessException;
import com.freightflow.exception.ResourceNotFoundException;
import com.freightflow.model.Cargo;
import com.freightflow.model.Cargo.CargoType;
import com.freightflow.model.Shipment;
import com.freightflow.model.Shipment.ShipmentStatus;
import com.freightflow.repository.CargoRepository;
import com.freightflow.repository.ShipmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for Cargo business logic.
 */
@Service
@Transactional
public class CargoService {

    private final CargoRepository cargoRepository;
    private final ShipmentRepository shipmentRepository;

    public CargoService(CargoRepository cargoRepository,
                        ShipmentRepository shipmentRepository) {
        this.cargoRepository = cargoRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional(readOnly = true)
    public List<Cargo> getAllCargos() {
        return cargoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cargo> getCargosByType(CargoType type) {
        return cargoRepository.findByType(type);
    }

    @Transactional(readOnly = true)
    public Cargo getCargoById(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Груз", id));
    }

    public Cargo createCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public Cargo updateCargo(Long id, Cargo cargoDetails) {
        Cargo cargo = getCargoById(id);
        cargo.setName(cargoDetails.getName());
        cargo.setWeightKg(cargoDetails.getWeightKg());
        cargo.setVolumeCubicMeters(cargoDetails.getVolumeCubicMeters());
        cargo.setType(cargoDetails.getType());
        return cargoRepository.save(cargo);
    }

    public void deleteCargo(Long id) {
        Cargo cargo = getCargoById(id);

        // Check if cargo is used in any IN_PROGRESS shipment
        List<Shipment> activeShipments = shipmentRepository
                .findByStatusAndCargoId(ShipmentStatus.IN_PROGRESS, id);

        if (!activeShipments.isEmpty()) {
            throw new BusinessException(
                    "Невозможно удалить груз: он используется в активном рейсе");
        }

        cargoRepository.delete(cargo);
    }

    @Transactional(readOnly = true)
    public boolean hasActiveShipments(Long cargoId) {
        List<Shipment> activeShipments = shipmentRepository
                .findByStatusAndCargoId(ShipmentStatus.IN_PROGRESS, cargoId);
        return !activeShipments.isEmpty();
    }

    @Transactional(readOnly = true)
    public int countCargosByType(CargoType type) {
        return cargoRepository.countByType(type);
    }
}
