package com.freightflow.service;

import com.freightflow.exception.BusinessException;
import com.freightflow.exception.ResourceNotFoundException;
import com.freightflow.model.Cargo;
import com.freightflow.model.Carrier;
import com.freightflow.model.Shipment;
import com.freightflow.model.Shipment.ShipmentStatus;
import com.freightflow.repository.CargoRepository;
import com.freightflow.repository.CarrierRepository;
import com.freightflow.repository.ShipmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for Shipment business logic.
 */
@Service
@Transactional
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final CargoRepository cargoRepository;
    private final CarrierRepository carrierRepository;

    public ShipmentService(ShipmentRepository shipmentRepository,
                           CargoRepository cargoRepository,
                           CarrierRepository carrierRepository) {
        this.shipmentRepository = shipmentRepository;
        this.cargoRepository = cargoRepository;
        this.carrierRepository = carrierRepository;
    }

    @Transactional(readOnly = true)
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Shipment> getShipmentsByStatus(ShipmentStatus status) {
        return shipmentRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Shipment> getShipmentsByCarrierId(Long carrierId) {
        return shipmentRepository.findByCarrierId(carrierId);
    }

    @Transactional(readOnly = true)
    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Рейс", id));
    }

    public Shipment createShipment(Shipment shipment) {
        // Validate cargo exists
        Cargo cargo = cargoRepository.findById(shipment.getCargo().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Груз",
                        shipment.getCargo().getId()));

        // Validate carrier exists
        Carrier carrier = carrierRepository.findById(shipment.getCarrier().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Перевозчик",
                        shipment.getCarrier().getId()));

        // Validate carrier capacity
        if (carrier.getCapacityKg() < cargo.getWeightKg()) {
            throw new BusinessException(
                    String.format(
                            "Грузоподъемность перевозчика (%.2f кг) меньше веса груза (%.2f кг)",
                            carrier.getCapacityKg(), cargo.getWeightKg()));
        }

        // Validate transport type compatibility
        if (!isTransportCompatible(cargo.getType(), carrier.getTransportType())) {
            throw new BusinessException(
                    String.format(
                            "Тип транспорта %s не подходит для груза типа %s",
                            carrier.getTransportType(), cargo.getType()));
        }

        shipment.setCargo(cargo);
        shipment.setCarrier(carrier);
        shipment.setStatus(ShipmentStatus.CREATED);

        return shipmentRepository.save(shipment);
    }

    public Shipment updateShipment(Long id, Shipment shipmentDetails) {
        Shipment shipment = getShipmentById(id);

        // Allow updating status and scheduled departure
        if (shipmentDetails.getStatus() != null) {
            shipment.setStatus(shipmentDetails.getStatus());
        }

        if (shipmentDetails.getScheduledDeparture() != null) {
            shipment.setScheduledDeparture(shipmentDetails.getScheduledDeparture());
        }

        if (shipmentDetails.getOrigin() != null) {
            shipment.setOrigin(shipmentDetails.getOrigin());
        }

        if (shipmentDetails.getDestination() != null) {
            shipment.setDestination(shipmentDetails.getDestination());
        }

        return shipmentRepository.save(shipment);
    }

    public Shipment cancelShipment(Long id) {
        Shipment shipment = getShipmentById(id);

        if (shipment.getStatus() == ShipmentStatus.DELIVERED) {
            throw new BusinessException(
                    "Невозможно отменить доставленный рейс");
        }

        shipment.setStatus(ShipmentStatus.CANCELLED);
        return shipmentRepository.save(shipment);
    }

    /**
     * Checks if the transport type is compatible with the cargo type.
     * REFRIGERATED cargo requires REFRIGERATOR transport.
     * Other cargo types can be transported by any transport type.
     */
    private boolean isTransportCompatible(Cargo.CargoType cargoType,
                                          Carrier.TransportType transportType) {
        if (cargoType == Cargo.CargoType.REFRIGERATED) {
            return transportType == Carrier.TransportType.REFRIGERATOR;
        }
        // DANGEROUS cargo might require special handling, but for simplicity
        // we allow any transport type except VAN for heavy dangerous goods
        if (cargoType == Cargo.CargoType.DANGEROUS) {
            return transportType != Carrier.TransportType.VAN;
        }
        return true;
    }
}
