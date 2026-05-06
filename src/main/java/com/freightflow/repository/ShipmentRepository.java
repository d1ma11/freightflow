package com.freightflow.repository;

import com.freightflow.model.Shipment;
import com.freightflow.model.Shipment.ShipmentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Shipment entity.
 */
@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    List<Shipment> findByStatus(ShipmentStatus status);

    List<Shipment> findByCarrierId(Long carrierId);

    List<Shipment> findByCargoId(Long cargoId);

    List<Shipment> findByStatusAndCarrierId(ShipmentStatus status, Long carrierId);

    List<Shipment> findByStatusAndCargoId(ShipmentStatus status, Long cargoId);
}
