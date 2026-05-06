package com.freightflow.service;

import com.freightflow.exception.BusinessException;
import com.freightflow.exception.ResourceNotFoundException;
import com.freightflow.model.Carrier;
import com.freightflow.model.Carrier.TransportType;
import com.freightflow.model.Shipment;
import com.freightflow.model.Shipment.ShipmentStatus;
import com.freightflow.repository.CarrierRepository;
import com.freightflow.repository.ShipmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for Carrier business logic.
 */
@Service
@Transactional
public class CarrierService {

    private final CarrierRepository carrierRepository;
    private final ShipmentRepository shipmentRepository;

    public CarrierService(CarrierRepository carrierRepository,
                          ShipmentRepository shipmentRepository) {
        this.carrierRepository = carrierRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @Transactional(readOnly = true)
    public List<Carrier> getAllCarriers() {
        return carrierRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Carrier> getCarriersByTransportType(TransportType transportType) {
        return carrierRepository.findByTransportType(transportType);
    }

    @Transactional(readOnly = true)
    public Carrier getCarrierById(Long id) {
        return carrierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Перевозчик", id));
    }

    public Carrier createCarrier(Carrier carrier) {
        return carrierRepository.save(carrier);
    }

    public Carrier updateCarrier(Long id, Carrier carrierDetails) {
        Carrier carrier = getCarrierById(id);
        carrier.setName(carrierDetails.getName());
        carrier.setContactPerson(carrierDetails.getContactPerson());
        carrier.setPhone(carrierDetails.getPhone());
        carrier.setTransportType(carrierDetails.getTransportType());
        carrier.setCapacityKg(carrierDetails.getCapacityKg());
        return carrierRepository.save(carrier);
    }

    public void deleteCarrier(Long id) {
        Carrier carrier = getCarrierById(id);

        // Check if carrier is used in any IN_PROGRESS shipment
        List<Shipment> activeShipments = shipmentRepository
                .findByStatusAndCarrierId(ShipmentStatus.IN_PROGRESS, id);

        if (!activeShipments.isEmpty()) {
            throw new BusinessException(
                    "Невозможно удалить перевозчика: он используется в активном рейсе");
        }

        carrierRepository.delete(carrier);
    }

    @Transactional(readOnly = true)
    public boolean hasActiveShipments(Long carrierId) {
        List<Shipment> activeShipments = shipmentRepository
                .findByStatusAndCarrierId(ShipmentStatus.IN_PROGRESS, carrierId);
        return !activeShipments.isEmpty();
    }
}
